Для компиляции нужно пропустить этап тестов используя команду: `clean package -DskipTests -f pom.xml`
Если отвалится перевод/генерация цитат, нужно поменять модель в класссе AITranslationGenerationUnit в методах generateText и translateText на любую бесплатную с `openrouter.ai`, например: `deepseek/deepseek-chat-v3-0324:free`; `google/gemini-2.0-flash-exp:free`; `qwen/qwen3-14b:free`
