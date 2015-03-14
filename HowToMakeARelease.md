# Introduction #
These instructions are for Committers on this project to help us to release new versions of the Daisy Reader software consistently.

# Details #
  * svn ci (checkin / commit) for the code that will be included in the new release
  * `svn update` which updates the revision number on the local development machine so it is accurate in the release we build.
  * Clean Project in Eclipse; which also causes the release number to be updated in `AndroidManifest.xml` and thence in the `DaisyReader` application.
  * Build the Project
  * Copy `DaisyReader.apk` in the `bin` folder to `DaisyReader-`_nnn_`.apk` but replace _nnn_ with the release number (use the value reported by the `svn update` command).
  * Login to this website with your account that has Commit rights
  * Create a new download on the Downloads section.
    * Add an appropriate description, etc.
  * Decide whether to make it a featured download, if so set the appropriate property for the new download
  * Consider updating the homepage of the project (from the Administer tab) to include the new release.
  * Consider deprecating old Release(s)