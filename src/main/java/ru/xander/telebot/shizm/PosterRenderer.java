package ru.xander.telebot.shizm;

import ru.xander.telebot.util.Fonts;
import ru.xander.telebot.util.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @author Alexander Shakhov
 */
public class PosterRenderer {

    private final boolean debugMode;

    public PosterRenderer() {
        this(false);
    }

    public PosterRenderer(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public InputStream render(Poster poster, String text) {
        return render(poster, text, Fonts.getRandom().getFont());
    }

    public InputStream render(Poster poster, String text, Font font) {
        try {
            BufferedImage image = Utils.readResource(poster.getResource(), ImageIO::read);
            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

            drawPoster(graphics, poster, text, font);

            graphics.dispose();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Cannot render poster: " + e.getMessage(), e);
        }
    }

    private void drawPoster(Graphics2D graphics, Poster poster, String str, Font font) {
        debug("-----------------------------------------------------");

        FontMetrics fontMetrics = graphics.getFontMetrics(font);
        debug("Font: " + font.getName());

        PosterText text = splitText(str, fontMetrics, poster);
        debug("Width: " + text.getTotalWidth());
        debug("Height: " + text.getTotalHeight());
        text.getLines().forEach(this::debug);

        if (debugMode) {
            graphics.setColor(new Color(255, 0, 0, 100));
            graphics.fillRect(
                    poster.getBubbleLeft(), poster.getBubbleTop(),
                    poster.getBubbleWidth(), poster.getBubbleHeight());
        }

        final int bubbleWidth = text.getTotalWidth() + poster.getTextPadding() * 2;
        final int bubbleHeight = text.getTotalHeight() + poster.getTextPadding();

        final double scaleX = poster.getBubbleWidth() / (double) bubbleWidth;
        final double scaleY = poster.getBubbleHeight() / (double) bubbleHeight;
        debug("Scale X: " + scaleX);
        debug("Scale Y: " + scaleY);

        final int scaledBubbleWidth = (scaleX < 1) ? (int) (scaleX * bubbleWidth) : bubbleWidth;
        final int scaledBuddleHeight = (scaleY < 1) ? (int) (scaleY * bubbleHeight) : bubbleHeight;

        final int bubbleLeft = getBubbleLeft(poster, scaledBubbleWidth);
        final int bubbleTop = getBubbleTop(poster, scaledBuddleHeight);

        graphics.setColor(getColor(poster.getBubbleColor()));
        graphics.fillRoundRect(bubbleLeft, bubbleTop, scaledBubbleWidth, scaledBuddleHeight, 25, 25);

        int textHeight = text.getLineHeight();
        if ((scaleX < 1) || (scaleY < 1)) {
            AffineTransform transform = graphics.getTransform();
            transform.scale(scaleX < 1 ? scaleX : 1, scaleY < 1 ? scaleY : 1);
            font = font.deriveFont(transform);
            fontMetrics = graphics.getFontMetrics(font);
            textHeight = fontMetrics.getHeight();
        }

        graphics.setFont(font);
        graphics.setColor(getColor(poster.getTextColor()));
        int textTop = bubbleTop;
        int textPadding = (scaleX < 1) ? (int) (poster.getTextPadding() * scaleX) : poster.getTextPadding();
        for (String line : text.getLines()) {
            textTop += textHeight;

            int textWidth = fontMetrics.stringWidth(line);

            final int textLeft;
            switch (poster.getTextAlign()) {
                case CENTER:
                    textLeft = (int) (bubbleLeft + ((scaledBubbleWidth - textWidth) / (double) 2));
                    break;
                case RIGHT:
                    textLeft = bubbleLeft + scaledBubbleWidth - textWidth - textPadding;
                    break;
                case LEFT:
                default:
                    textLeft = bubbleLeft + textPadding;
                    break;
            }

            graphics.drawString(line, textLeft, textTop);
        }
    }

    private PosterText splitText(String text, FontMetrics fontMetrics, Poster poster) {
        int totalWidth = fontMetrics.stringWidth(text);
        int height = fontMetrics.getHeight();

        double perfectRatio = poster.getBubbleWidth() / (double) poster.getBubbleHeight();
        debug("Aspect Ratio: " + perfectRatio);

        int linesCount = 1;
        double nearestRatio = Double.MAX_VALUE;
        for (; linesCount <= 10; linesCount++) {
            double aspectRatio = (totalWidth / (double) linesCount) / (height * (double) linesCount);
            double diff = Math.abs(aspectRatio - perfectRatio);
            debug(linesCount + "\t" + aspectRatio + "\t" + diff);
            if (nearestRatio > diff) {
                nearestRatio = diff;
            } else {
                linesCount--;
                break;
            }
        }

        debug("Lines count: " + linesCount);
        if (linesCount == 1) {
            return new PosterText(totalWidth, height, height, Collections.singletonList(text));
        }

        int lineSize = (int) (text.length() / (double) linesCount);
        debug("Line size: " + lineSize);

        Collection<String> lines = new LinkedList<>();

        String[] splitted = text.split(" ");
        StringBuilder str = new StringBuilder();
        int maxWidth = 0;
        for (String s : splitted) {
            if (s.isEmpty()) {
                continue;
            }
            int length = str.length();
            if (length == 0) {
                str.append(s);
                continue;
            }

            if ((length + s.length()) <= lineSize) {
                str.append(' ').append(s);
                continue;
            }

            int minDiff = Math.abs(lineSize - length);
            int maxDiff = Math.abs(lineSize - (length + s.length() + 1));
            if (minDiff > maxDiff) {
                str.append(' ').append(s);
                String line = str.toString();
                maxWidth = Math.max(fontMetrics.stringWidth(line), maxWidth);
                lines.add(line);
                str.setLength(0);
            } else {
                String line = str.toString();
                maxWidth = Math.max(fontMetrics.stringWidth(line), maxWidth);
                lines.add(line);
                str.setLength(0);
                str.append(s);
            }
        }

        if (str.length() > 0) {
            String line = str.toString();
            maxWidth = Math.max(fontMetrics.stringWidth(line), maxWidth);
            lines.add(line);
        }

        return new PosterText(maxWidth, height * lines.size(), height, lines);
    }

    private void debug(String text) {
        if (debugMode) {
            System.out.println(text);
        }
    }

    private static int getBubbleLeft(Poster poster, int bubbleWidth) {
        int bubbleLeft;
        switch (poster.getBubbleHAlign()) {
            case CENTER:
                bubbleLeft = (int) (poster.getBubbleLeft() + ((poster.getBubbleWidth() - bubbleWidth) / (double) 2));
                break;
            case RIGHT:
                bubbleLeft = poster.getBubbleLeft() + (poster.getBubbleWidth() - bubbleWidth);
                break;
            case LEFT:
            default:
                bubbleLeft = poster.getBubbleLeft();
                break;
        }
        return bubbleLeft;
    }

    private static int getBubbleTop(Poster poster, int bubbleHeight) {
        int bubbleTop;
        switch (poster.getBubbleVAlign()) {
            case MIDDLE:
                bubbleTop = (int) (poster.getBubbleTop() + ((poster.getBubbleHeight() - bubbleHeight) / (double) 2));
                break;
            case BOTTOM:
                bubbleTop = poster.getBubbleTop() + (poster.getBubbleHeight() - bubbleHeight);
                break;
            case TOP:
            default:
                bubbleTop = poster.getBubbleTop();
                break;
        }
        return bubbleTop;
    }

    private static Color getColor(int[] argb) {
        if (argb.length == 3) {
            return new Color(argb[0], argb[1], argb[2], 255);
        } else {
            return new Color(argb[0], argb[1], argb[2], argb[3]);
        }
    }
}
