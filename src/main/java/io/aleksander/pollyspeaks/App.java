package io.aleksander.pollyspeaks;

import com.amazonaws.SdkClientException;
import io.aleksander.pollyspeaks.controller.MainController;
import io.aleksander.pollyspeaks.utils.StringResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import static io.aleksander.pollyspeaks.utils.StringResource.AWS_SDK_ERROR;
import static io.aleksander.pollyspeaks.utils.StringResource.FAILED_TO_START_ERROR;

public class App {
  private static final Logger logger = LogManager.getLogger(App.class);

  public static void main(String[] args) {
    SwingUtilities.invokeLater(
        () -> {
          try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new MainController();
          } catch (SdkClientException exception) {
            JOptionPane.showMessageDialog(
                null,
                StringResource.getString(AWS_SDK_ERROR),
                StringResource.getString(StringResource.ERROR),
                JOptionPane.ERROR_MESSAGE);
            logger.error("No AWS Credentials/Region found. Unable to start application.", exception);
          } catch (Exception exception) {
            JOptionPane.showMessageDialog(
                null,
                StringResource.getString(FAILED_TO_START_ERROR),
                StringResource.getString(StringResource.ERROR),
                JOptionPane.ERROR_MESSAGE);
            logger.error("Failed to instantiate application.", exception);
          }
        });
  }
}
