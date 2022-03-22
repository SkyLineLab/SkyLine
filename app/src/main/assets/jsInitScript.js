const client = BotProject.getClient();

client.on('message', data => {
    if (data.text === '!ping') {
        data.room.send('pong!');
    }
});