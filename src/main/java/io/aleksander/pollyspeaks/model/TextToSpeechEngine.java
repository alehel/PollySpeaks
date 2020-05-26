package io.aleksander.pollyspeaks.model;

import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClientBuilder;
import com.amazonaws.services.polly.model.DescribeVoicesRequest;
import com.amazonaws.services.polly.model.DescribeVoicesResult;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.Voice;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TextToSpeechEngine extends AbstractModel {
  private final AmazonPolly polly;
  private final List<String> availableLanguages;
  private final List<Voice> allVoices;
  private List<Voice> availableVoices;
  private String language;
  private String voiceId;
  private boolean useSSML = false;

  public TextToSpeechEngine() {
    polly = AmazonPollyClientBuilder.defaultClient();
    DescribeVoicesRequest voicesRequest = new DescribeVoicesRequest();
    DescribeVoicesResult voicesResult = polly.describeVoices(voicesRequest);

    availableLanguages =
        voicesResult.getVoices().stream()
            .map(Voice::getLanguageName)
            .distinct()
            .sorted(String::compareTo)
            .collect(Collectors.toList());

    allVoices = voicesResult.getVoices();
    availableVoices = voicesResult.getVoices();
  }

  public void setUseSSML(boolean useSSML) {
    boolean oldValue = this.useSSML;
    this.useSSML = useSSML;
    firePropertyChange("useSSML", oldValue, useSSML);
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    String oldValue = this.language;
    this.language = language;
    firePropertyChange("language", oldValue, language);

    List<Voice> availableVoicesBasedOnLanguage =
        allVoices.stream()
            .filter(voice -> voice.getLanguageName().equals(language))
            .collect(Collectors.toList());

    setAvailableVoices(availableVoicesBasedOnLanguage);
  }

  public List<Voice> getAvailableVoices() {
    return availableVoices;
  }

  public void setAvailableVoices(List<Voice> voices) {
    List<Voice> oldValue = this.availableVoices;
    this.availableVoices = voices;
    firePropertyChange(
        "availableVoices",
        Collections.unmodifiableList(oldValue),
        Collections.unmodifiableList(voices));
  }

  public List<String> getAvailableLanguages() {
    return availableLanguages;
  }

  public void setVoiceId(String voiceId) {
    String oldValue = this.voiceId;
    this.voiceId = voiceId;
    firePropertyChange("voiceId", oldValue, voiceId);
  }

  public InputStream convertTextToSpeech(String text) {
    return convertTextToSpeech(text, OutputFormat.Mp3);
  }

  public InputStream convertTextToSpeech(String text, OutputFormat outputFormat) {
    SynthesizeSpeechRequest synthReq =
        new SynthesizeSpeechRequest()
            .withText(text)
            .withVoiceId(voiceId)
            .withOutputFormat(outputFormat)
            .withTextType(useSSML ? "ssml" : "text");
    SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);

    return synthRes.getAudioStream();
  }
}
