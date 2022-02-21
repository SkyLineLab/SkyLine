# SkyLine
Unofficial KakaoTalk ChatBot for Android
## Script Engine
- [x] [javascript](https://github.com/oracle/graaljs)
- [ ] kotlin script
## It support NodeJS?
Although it is not possible to implement all the node modules, we plan to implement important modules (buffer, crypto, timer, etc.).

Please refer to [node.md](https://github.com/SkyLineLab/SkyLine/blob/master/node.md) for details.
## Why GraalJS?
The Rhino engine also supports ES2015 poorly, while the GraalJS supports the majority of grammar until ES2022.
### Warning
`However, GraalJS does not yet support Android.
We modified the part that doesn't work on Android and changed it to work on Android.`
#### Android API Support
- [x] over 26
- [x] over 24
- [ ] over 21
## example
```javascript
const client = BotProject.getClient();

client.on('message', (data) => {
    if (data.message === '!ping') {
        data.room.send('pong!');
    }
});
```
