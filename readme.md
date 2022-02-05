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
#### Android API Support
- [x] over 26
- [x] over 24
- [?] over 21
`API 24 or higher guarantees normal operation, but 21 to 23 are theoretically possible, but no bugchecks have been made.`
## example
```javascript
const client = BotProject.getClient();

client.on('message', (data) => {
    if (data.message === '!ping') {
        data.room.send('pong!');
    }
});
```
