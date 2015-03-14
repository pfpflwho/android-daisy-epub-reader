# Introduction #

The DaisyReader is not yet available on the Android Market. You can install it directly to your phone in several ways. This wiki page describes the options I know about. Please add a comment at the end of this page if you know of additional ways to install the software.

# Enabling the installation of non-Market applications #
As standard, most Android devices limit installation of applications to those downloaded from Android Market. many of these devices include a setting to allow applications to be installed from unknown sources.

The setting (on an English language Android phone) is reached as follows:
`Settings->Application settings->Unknown sources` - make sure the tick-box is selected. (Un-select it to prohibit additional applications from being installed from non-Market sources).

# Ways to install the application #
Follow the instructions for the way you would like to install the application. In each case you will eventually reach the point where you should be presented with an option to install the application. Follow the instructions on the phone to complete the installation.

## From an email ##
Send the DaisyReader apk file as an attachment in an email message to an account configured on the Android device. When you open the email on the device.

## From a web page ##
Open a web page e.g. http://code.google.com/p/android-daisy-epub-reader/downloads/list then select the version of the DaisyReader you want to install (you many need to follow several links to get to the actual download).

# Installation using Android development tools #
If you are willing to install the Android SDK then you can also use the `adb` command to install applications using USB and other connections. Search online for instructions for how to use `adb install` to install the application.

# Uninstalling the application #
Sometimes you may need to install a previously installed version of the DaisyReader e.g. if you compile a version of the program yourself and then want to install a version from this web site. The main reason you would need to uninstall is when the software was built from another computer using a debug signing key. Don't worry if this doesn't make much sense. The uninstallation process is relatively simple.

## Uninstallation on the device ##
`Settings->Application settings->Manage applications` then select the Android Daisy 2.02 Reader and then select the `Uninstall` button.

## Uninstallation using adb ##
If you are using the Android SDK you can use `adb uninstall com.ader` to deinstall the application.

## Troubleshooting problems ##
The following wiki page has some advice on investigating and resolving installation and other problems DebuggingInstallationAndOtherProblems