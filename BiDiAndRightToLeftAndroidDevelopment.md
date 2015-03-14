# Introduction #

I've started the process of adding support for Right-To-Left (RTL) languages such as Arabic and Hebrew text. I'm familiar with some of the challenges and technologies related to supporting and testing BiDi and RTL content for Web content, however I've not managed to find much information for the Android platform.

The challenges are exacerbated when we need to consider Text-To-Speech (TTS) and Accessibility Services. So I decided to share my thoughts, research and work publicly in case it's a) useful to you b) you're able to contribute and help us to improve this application in particular and Android apps in general.

# Features of Android's TTS Service #
  * Mono-lingual - can only speak one language so multi-language content would be a challenge to support e.g. where the text of a book is in French and the device / application are set to German.
  * Relies on third-party support for most languages, including Arabic.

# Features of Android's Accessibility Services #
  * TBD
