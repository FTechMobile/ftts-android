# FTTS SDK Android



## 1. Install TTS SDK
## 2. SDK Integration
#### Initialize SDK
**java:**
```java
@Override
public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    ...
    TTSManager.init(getApplicationContext());
}
```
**kotlin:**
```kotlin
override fun onCreate() {
        super.onCreate()
        ...
        TTSManager.init(applicationContext)
    }
```
#### Init gateway
**java:**
```java
TTSManager.initGateway(appId, secretKey, new IInitGatewayCallback() {
    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail(@Nullable AppException error) {

    }
});
```
**kotlin:**
```kotlin
TTSManager.initGateway(appId, secretKey, object : IInitGatewayCallback {
    override fun onSuccess() {
    
    }

    override fun onFail(error: AppException?) {

    }
})
```
#### Register STT callback
**After registration, the SDK will return the corresponding status in the callback**
| Status     | Description |
| ----------- | ----------- |
| onStart      | Called at start record       |
| onPlaying   | Called while audio playing       |
| onSuccess      | Called when return result       |
| onFail   | Called when an error occurs in process  |
**java:**
```java
TTSManager.registerTTSCallback(new ISTTCallback() {
    @Override
    public void onStart() {
        
    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onFail(@Nullable AppException error) {

    }

    @Override
    public void onSuccess(@NonNull String result) {

    }
});
```
**kotlin:**
```kotlin
TTSManager.registerTTSCallback(object : ISTTCallback {
    override fun onStart() {

    }

    override fun onPlaying() {
    
    }

    override fun onFail(error: AppException?) {

    }

    override fun onSuccess(urlAudio: String) {

    })
})
```
## 3. SDK Features
#### Get language config
* Get list language config used to tts param
* When sucess, result is `listLanguage`, it will be called on `onSuccess()` in callback
* When fail, it will called on `onFail()` in callback
  **java:**
```java
TTSManager.getLanguageConfig(new ILanguageConfigCallback() {
    @Override
    public void onSuccess(@NonNull List<LanguageConfigData> listLanguage) {
        
    }

    @Override
    public void onFail(@Nullable AppException error) {

    }
});
```
**kotlin:**
```kotlin
TTSManager.getLanguageConfig(object : ILanguageConfigCallback {
    override fun onSuccess(listLanguage: List<LanguageConfigData>) {

    }

    override fun onFail(error: AppException?) {
    
    }
})
```
#### Start TTS
* Used to start TTS
* When successful start, it will be called on `onStart()` in callback
* If `isAutoPlayAudio = true`, when have result sdk will play audio and called on `onPlaying()` in callback, after play audio completed, `audioUrl` will be return on `onSuccess` in callback.
* If `isAutoPlayAudio = false`, when have result `audioUrl` will be return on `onSuccess` in callback.
* When fails, it will be processed at callback `onFail()` in callback.

**java:**
```java
TTSManager.startTTS(language, voice, content, true);
```
**kotlin:**
```kotlin
TTSManager.startTTS(language, voice, content, true)
```