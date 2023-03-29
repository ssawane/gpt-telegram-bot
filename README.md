# gpt-telegram-bot

Telegram bot for sending requests and receiving responses from ChatGPT of the OpenAi platform.

Together with the last request, the history of correspondence is sent to the OpenAi server for understanding the context by artificial intelligence (up to 4096 tokens), provided that the age of the message does not exceed 60 minutes.

To get acquainted with the service, new users are provided with 5,000 tokens.

The bot allows you to find out the balance of tokens, contact support.

Admin panel allows access to monitoring metrics (Prometheus, Grafana)

The data is logged and written to a text file.

Database migration is implemented using Flyway.

The application is packaged in a docker container.

The project is deployed on a remote server (Debian 11).

Link to the bot: https://t.me/ssawanebot
