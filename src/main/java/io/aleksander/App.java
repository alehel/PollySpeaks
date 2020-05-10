package io.aleksander;

import com.amazonaws.SdkClientException;
import io.aleksander.controller.MainController;
import io.aleksander.utils.StringResource;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import static io.aleksander.utils.StringResource.AWS_SDK_ERROR;

public class App {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(
        new Runnable() {
          @Override
          public void run() {
            try {
              new MainController();
            } catch (SdkClientException exception) {
              JOptionPane.showMessageDialog(
                  null,
                  StringResource.getString(AWS_SDK_ERROR),
                  StringResource.getString(StringResource.ERROR),
                  JOptionPane.ERROR_MESSAGE);
            }
          }
        });
  }
}
