# SkyLine

Unofficial KakaoTalk ChatBot for Android

## Script Engine

- [x] [javascript](https://github.com/oracle/graaljs)

## It support NodeJS?

Although it is not possible to implement all the node modules, we plan to implement important modules (buffer, crypto, timer, etc.)

## Why GraalJS?

The Rhino engine also supports ES2015 poorly, while the GraalJS supports the majority of grammar until ES2022.

### Warning

> However, GraalJS does not yet support Android.
> We modified the part that doesn't work on Android and changed it to work on Android.

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

## DEBUG
### LOGGER

SkyLine uses the orhanobut logger.
### How to view SkyLine Log?
Among the logs, "PRETTY_LOGGER" is the log that SkyLine debugs.