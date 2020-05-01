package io.aleksander.utils;

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

public class PollyClient {
  private static final PollyClient instance = new PollyClient();
  private final AmazonPolly polly;
  private final DescribeVoicesResult voicesResult;

  private PollyClient() {
    polly = AmazonPollyClientBuilder.defaultClient();
    DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();
    voicesResult = polly.describeVoices(describeVoicesRequest);
  }

  public static PollyClient getInstance() {
    return instance;
  }

  public List<Voice> getVoices() {
    return voicesResult.getVoices();
  }

  public InputStream synthesizeText(String voiceId, String text) {
    SynthesizeSpeechRequest synthReq =
        new SynthesizeSpeechRequest()
            .withText(text)
            .withVoiceId(voiceId)
            .withOutputFormat(OutputFormat.Mp3);
    SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);

    return synthRes.getAudioStream();
  }
}
