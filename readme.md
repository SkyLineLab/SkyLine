# SkyLine

비공식 안드로이드용 카카오톡 알림 기반 봇

## 언어

- [eng](/readme-en.md)
- [한국어](/readme.md)

## 스크립트 엔진

- [x] [javascript](https://github.com/oracle/graaljs)

## `NodeJS`를 지원하나요?

아마, 모든 노드 모듈을 구현할순 없을겁니다. 그러나 중요한 몇몇 모듈을 구현할 계획이 있습니다 `(buffer, crypto, timers, 기타)`

## 왜 `GraalJS`를 사용하나요?

`라이노 엔진`은 ES2015도 제대로 구현하지 못하였습니다, 그러나 `GraalJS`는 ES2023까지 모두 지원합니다.

### 주의

> 하지만 `GraalJS`는 안드로이드를 제대로 지원하지 않습니다.
> 저희는 안드로이드에서 작동하지 않는 부분을 수정하여 작동되게 만들었습니다. 그러나 이로인한 메모리 누수가 발생할 수 도 있습니다.

#### 안드로이드 지원

- [x] 26 이상
- [x] 24 이상
- [ ] 21 이상
## 예제

```javascript
const client = BotProject.getClient();

client.on('message', (data) => {
    if (data.message === '!ping') {
        data.room.send('pong!');
    }
});
```

## 디버그
### 로거

`SkyLine`은 `orhanobut` 로거를 사용합니다.
### 어떻게 하면 `SkyLine`의 로그를 볼 수 있습니까?
로그캣에서 `PRETTY_LOGGER`가 있으면 `SkyLine`의 로그입니다.