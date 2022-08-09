const client = BotProject.getClient();

client.on('message', data => {
    if (data.message === '!ping') {
        data.room.send('pong!');
    }
});