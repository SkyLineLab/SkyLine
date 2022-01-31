# SkyLine
Unofficial KakaoTalk ChatBot for Android
## JavaScript Engine
- [x] graaljs
- [ ] rhino
## Why GraalJS?
The Rhino engine also supports ES2015 poorly, while the GraalJS supports the majority of grammar until ES2022.
### Warning
`However, GraalJS does not yet support Android.
We modified the part that doesn't work on Android and changed it to work on Android.`
## example
```javascript
const client = BotProject.getClient();

client.on('message', (data) => {
    if (data.message === '!ping') {
        data.room.send('pong!');
    }
});
```
