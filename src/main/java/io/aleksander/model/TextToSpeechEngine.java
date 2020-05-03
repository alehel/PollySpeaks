package io.aleksander.model;

import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClientBuilder;
import com.amazonaws.services.polly.model.DescribeVoicesRequest;
import com.amazonaws.services.polly.model.DescribeVoicesResult;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.Voice;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class TextToSpeechEngine {
  private final AmazonPolly polly;
  private String language;
  private String voiceId;
  private final List<String> availableLanguages;
  private final List<Voice> allVoices;

  public TextToSpeechEngine() {
    polly = AmazonPollyClientBuilder.defaultClient();
    DescribeVoicesRequest voicesRequest = new DescribeVoicesRequest();
    DescribeVoicesResult voicesResult = polly.describeVoices(voicesRequest);

    availableLanguages = voicesResult.getVoices().stream()
        .map(Voice::getLanguageName)
        .distinct()
        .sorted(String::compareTo)
        .collect(Collectors.toList());

    allVoices = voicesResult.getVoices();
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public List<Voice> getAvailableVoices() {
    if(language == null) {
      return allVoices;
    } else {
      return allVoices.stream()
          .filter(voice -> voice.getLanguageName().equals(language))
          .collect(Collectors.toList());
    }
  }

  public List<String> getAvailableLanguages() {
    return availableLanguages;
  }

  public void setVoiceId(String voiceId) {
    this.voiceId = voiceId;
  }

  public InputStream convertTextToSpeech(String text) {
    SynthesizeSpeechRequest synthReq =
        new SynthesizeSpeechRequest()
            .withText(text)
            .withVoiceId(voiceId)
            .withOutputFormat(OutputFormat.Mp3);
    SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);

    return synthRes.getAudioStream();
  }
}
