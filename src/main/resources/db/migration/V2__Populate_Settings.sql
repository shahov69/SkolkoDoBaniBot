insert into setting (id, name, value, default_value)
values (1, 'ACTIVE_CHAT_ID', null, null),
       (2, 'ADMIN_ID', null, null),
       (3, 'ENABLE_BOT', 'true', 'true'),
       (4, 'ENABLE_ILUSHIZM', 'true', 'true'),
       (5, 'ENABLE_KIRUSHIZM', 'true', 'true'),
       (6, 'STICKER_HELLO', 'CAADAgADNxkAAuX3UgM9_oYuM8udiwI', 'CAADAgADGAAD5NdGDj8TYTfHnZ7gAg'),
       (7, 'STICKER_PICTURE_SET', 'CAADAgADXRkAAuX3UgNM4iWkZTPitwI', 'CAADAgADOAEAAhZ8aAP0b0MaIxsr8QI'),
       (8, 'TEXT_HELP', '<code>/hello</code> поприветствовать бота
<code>/howmuch</code> сколько времени до баньки
<code>/happybyozday</code> поздравить бота с днём рождения
<code>/set %startDateTime% %endDateTime%</code> установить время баньки (в формате yyyyMMdd-HHmmss)
<code>/unset</code> сбросить время баньки
<code>/pikcha</code> посмотреть картинку
<code>/weather</code> погодка
<code>/random_ilya</code> случайный илюшизм
<code>/add_ilya</code> добавить новый илюшизм
<code>/help</code> хелп
<code>/пикча для бани</code> установить пикчу для баньки', '<code>/hello</code> поприветствовать бота
<code>/howmuch</code> сколько времени до баньки
<code>/happybyozday</code> поздравить бота с днём рождения
<code>/set %startDateTime% %endDateTime%</code> установить время баньки (в формате yyyyMMdd-HHmmss)
<code>/unset</code> сбросить время баньки
<code>/pikcha</code> посмотреть картинку
<code>/weather</code> погодка
<code>/random_ilya</code> случайный илюшизм
<code>/add_ilya</code> добавить новый илюшизм
<code>/help</code> хелп
<code>/пикча для бани</code> установить пикчу для баньки'),
       (9, 'TEXT_HOWMUCH_AFTER', 'Банька закончилась :disappointed: С момента окончания прошло ${TIME}

в наносекундах: ${NANOS}
в микросекундах: ${MICROS}
в миллисекундах: ${MILLIS}
в секундах: ${SECONDS}
в минутах: ${MILLIS}
в часах: ${HOURS}
в днях: ${DAYS}', 'Банька закончилась :disappointed: С момента окончания прошло ${TIME}

в наносекундах: ${NANOS}
в микросекундах: ${MICROS}
в миллисекундах: ${MILLIS}
в секундах: ${SECONDS}
в минутах: ${MILLIS}
в часах: ${HOURS}
в днях: ${DAYS}'),
       (10, 'TEXT_HOWMUCH_BEFORE', 'До баньки осталось ${TIME} ⏰

в наносекундах:     ${NANOS}
в микросекундах:  ${MICROS}
в миллисекундах:  ${MILLIS}
в секундах:                ${SECONDS}
в минутах:                 ${MINUTES}
в часах:                       ${HOURS}
в днях:                         ${DAYS}

P.S.: главно чтоб ебло на подушке не забыть 👾', 'До баньки осталось ${TIME} ⏰

в наносекундах:     ${NANOS}
в микросекундах:  ${MICROS}
в миллисекундах:  ${MILLIS}
в секундах:                ${SECONDS}
в минутах:                 ${MINUTES}
в часах:                       ${HOURS}
в днях:                         ${DAYS}

P.S.: главно чтоб ебло на подушке не забыть 👾'),
       (11, 'TEXT_HOWMUCH_ONAIR', 'Банька уже в процессе 🕺

в наносекундах: ${NANOS}
в микросекундах: ${MICROS}
в миллисекундах: ${MILLIS}
в секундах: ${SECONDS}
в минутах: ${MILLIS}
в часах: ${HOURS}', 'Банька уже в процессе 🕺

в наносекундах: ${NANOS}
в микросекундах: ${MICROS}
в миллисекундах: ${MILLIS}
в секундах: ${SECONDS}
в минутах: ${MILLIS}
в часах: ${HOURS}'),
       (12, 'TEXT_PICTURE_SET', '吸うディック, 同性愛者 /pikcha', 'пикча установлена, проверь /pikcha'),
       (13, 'TEXT_UNKNOWN', 'あなた自身をクソ始めなさい!', 'Чо хуйню творишь');