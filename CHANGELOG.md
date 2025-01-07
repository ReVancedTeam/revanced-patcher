# [21.1.0-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v21.0.0...v21.1.0-dev.1) (2024-12-07)


### Features

* Add identity hash code to unnamed patches ([88a3252](https://github.com/ReVanced/revanced-patcher/commit/88a325257494939a79fb30dd51d60c5c52546755))

# [21.0.0](https://github.com/ReVanced/revanced-patcher/compare/v20.0.2...v21.0.0) (2024-11-05)


### Bug Fixes

* Match fingerprint before delegating the match property ([5d996de](https://github.com/ReVanced/revanced-patcher/commit/5d996def4d3de4e2bfc34562e5a6c7d89a8cddf0))
* Merge extension only when patch executes ([#315](https://github.com/ReVanced/revanced-patcher/issues/315)) ([aa472eb](https://github.com/ReVanced/revanced-patcher/commit/aa472eb9857145b53b49f843406a9764fbb7e5ce))


### Features

* Improve Fingerprint API ([#316](https://github.com/ReVanced/revanced-patcher/issues/316)) ([0abf1c6](https://github.com/ReVanced/revanced-patcher/commit/0abf1c6c0279708fdef5cb66b141d07d17682693))
* Improve various APIs  ([#317](https://github.com/ReVanced/revanced-patcher/issues/317)) ([b824978](https://github.com/ReVanced/revanced-patcher/commit/b8249789df8b90129f7b7ad0e523a8d0ceaab848))
* Move fingerprint match members to fingerprint for ease of access by using context receivers ([0746c22](https://github.com/ReVanced/revanced-patcher/commit/0746c22743a9561bae2284d234b151f2f8511ca5))


### Performance Improvements

* Use smallest lookup map for strings ([1358d3f](https://github.com/ReVanced/revanced-patcher/commit/1358d3fa10cb8ba011b6b89cfe3684ecf9849d2f))


### BREAKING CHANGES

* Various APIs have been changed.
* Many APIs have been changed.

# [21.0.0-dev.4](https://github.com/ReVanced/revanced-patcher/compare/v21.0.0-dev.3...v21.0.0-dev.4) (2024-11-05)


### Performance Improvements

* Use smallest lookup map for strings ([1358d3f](https://github.com/ReVanced/revanced-patcher/commit/1358d3fa10cb8ba011b6b89cfe3684ecf9849d2f))

# [21.0.0-dev.3](https://github.com/ReVanced/revanced-patcher/compare/v21.0.0-dev.2...v21.0.0-dev.3) (2024-11-05)


### Features

* Move fingerprint match members to fingerprint for ease of access by using context receivers ([0746c22](https://github.com/ReVanced/revanced-patcher/commit/0746c22743a9561bae2284d234b151f2f8511ca5))

# [21.0.0-dev.2](https://github.com/ReVanced/revanced-patcher/compare/v21.0.0-dev.1...v21.0.0-dev.2) (2024-11-01)


### Bug Fixes

* Match fingerprint before delegating the match property ([5d996de](https://github.com/ReVanced/revanced-patcher/commit/5d996def4d3de4e2bfc34562e5a6c7d89a8cddf0))

# [21.0.0-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v20.0.2...v21.0.0-dev.1) (2024-10-27)


### Bug Fixes

* Merge extension only when patch executes ([#315](https://github.com/ReVanced/revanced-patcher/issues/315)) ([aa472eb](https://github.com/ReVanced/revanced-patcher/commit/aa472eb9857145b53b49f843406a9764fbb7e5ce))


### Features

* Improve Fingerprint API ([#316](https://github.com/ReVanced/revanced-patcher/issues/316)) ([0abf1c6](https://github.com/ReVanced/revanced-patcher/commit/0abf1c6c0279708fdef5cb66b141d07d17682693))
* Improve various APIs  ([#317](https://github.com/ReVanced/revanced-patcher/issues/317)) ([b824978](https://github.com/ReVanced/revanced-patcher/commit/b8249789df8b90129f7b7ad0e523a8d0ceaab848))


### BREAKING CHANGES

* Various APIs have been changed.
* Many APIs have been changed.

## [20.0.2](https://github.com/ReVanced/revanced-patcher/compare/v20.0.1...v20.0.2) (2024-10-17)


### Bug Fixes

* Make it work on Android 12 and lower by using existing APIs ([#312](https://github.com/ReVanced/revanced-patcher/issues/312)) ([a44802e](https://github.com/ReVanced/revanced-patcher/commit/a44802ef4ebf59ae47213854ba761c81dadc51f3))

## [20.0.2-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v20.0.1...v20.0.2-dev.1) (2024-10-15)


### Bug Fixes

* Make it work on Android 12 and lower by using existing APIs ([#312](https://github.com/ReVanced/revanced-patcher/issues/312)) ([a44802e](https://github.com/ReVanced/revanced-patcher/commit/a44802ef4ebf59ae47213854ba761c81dadc51f3))

## [20.0.1](https://github.com/ReVanced/revanced-patcher/compare/v20.0.0...v20.0.1) (2024-10-13)


### Bug Fixes

* Check for class type exactly instead of with contains ([#310](https://github.com/ReVanced/revanced-patcher/issues/310)) ([69f2f20](https://github.com/ReVanced/revanced-patcher/commit/69f2f20fd99162f91cd9c531dfe47d00d3152ead))
* Make it work on Android by not using APIs from JVM unavailable to Android. ([2be6e97](https://github.com/ReVanced/revanced-patcher/commit/2be6e97817437f40e17893dfff3bea2cd4c3ff9e))
* Use non-nullable type for options ([ea6fc70](https://github.com/ReVanced/revanced-patcher/commit/ea6fc70caab055251ad4d0d3f1b5cf53865abb85))


### Performance Improvements

* Free memory earlier and remove negligible lookup maps ([d53aacd](https://github.com/ReVanced/revanced-patcher/commit/d53aacdad4ed3750ddae526fb307577ea36e6171))

## [20.0.1-dev.5](https://github.com/ReVanced/revanced-patcher/compare/v20.0.1-dev.4...v20.0.1-dev.5) (2024-10-11)


### Bug Fixes

* Use non-nullable type for options ([ea6fc70](https://github.com/ReVanced/revanced-patcher/commit/ea6fc70caab055251ad4d0d3f1b5cf53865abb85))

## [20.0.1-dev.4](https://github.com/ReVanced/revanced-patcher/compare/v20.0.1-dev.3...v20.0.1-dev.4) (2024-10-07)


### Bug Fixes

* Make it work on Android by not using APIs from JVM unavailable to Android. ([2be6e97](https://github.com/ReVanced/revanced-patcher/commit/2be6e97817437f40e17893dfff3bea2cd4c3ff9e))

## [20.0.1-dev.3](https://github.com/ReVanced/revanced-patcher/compare/v20.0.1-dev.2...v20.0.1-dev.3) (2024-10-03)


### Performance Improvements

* Free memory earlier and remove negligible lookup maps ([d53aacd](https://github.com/ReVanced/revanced-patcher/commit/d53aacdad4ed3750ddae526fb307577ea36e6171))

## [20.0.1-dev.2](https://github.com/ReVanced/revanced-patcher/compare/v20.0.1-dev.1...v20.0.1-dev.2) (2024-10-01)

## [20.0.1-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v20.0.0...v20.0.1-dev.1) (2024-09-18)


### Bug Fixes

* Check for class type exactly instead of with contains ([#310](https://github.com/ReVanced/revanced-patcher/issues/310)) ([69f2f20](https://github.com/ReVanced/revanced-patcher/commit/69f2f20fd99162f91cd9c531dfe47d00d3152ead))

# [20.0.0](https://github.com/ReVanced/revanced-patcher/compare/v19.3.1...v20.0.0) (2024-08-06)


### Bug Fixes

* Downgrade smali to fix dex compilation issue ([5227e98](https://github.com/ReVanced/revanced-patcher/commit/5227e98abfaa2ff1204eb20a0f2671f58c489930))
* Improve exception message wording ([5481d0c](https://github.com/ReVanced/revanced-patcher/commit/5481d0c54ccecc91cd8d15af1ba2d3285a33e5ab))
* Make constructor internal as supposed ([7f44174](https://github.com/ReVanced/revanced-patcher/commit/7f44174d91f0af0d50a83d80a7103c779241e094))
* Merge all extensions before initializing lookup maps ([8c4dd5b](https://github.com/ReVanced/revanced-patcher/commit/8c4dd5b3a309077fa9a3827b4931fc28b0517809))
* Use null for compatible package version when adding packages only ([736b3ee](https://github.com/ReVanced/revanced-patcher/commit/736b3eebbfdd7279b8d5fcfc5c46c9e3aadbee12))


### Features

* Add ability to create options outside of a patch ([d310246](https://github.com/ReVanced/revanced-patcher/commit/d310246852504b08a15f6376bbf25ac7c6fae76f))
* Convert APIs to Kotlin DSL ([#298](https://github.com/ReVanced/revanced-patcher/issues/298)) ([11a911d](https://github.com/ReVanced/revanced-patcher/commit/11a911dc674eb0801649949dd3f28dfeb00efe97))


### BREAKING CHANGES

* Various old APIs are removed, and DSL APIs are added instead.

# [20.0.0-dev.4](https://github.com/ReVanced/revanced-patcher/compare/v20.0.0-dev.3...v20.0.0-dev.4) (2024-08-06)


### Bug Fixes

* Improve exception message wording ([bd434ce](https://github.com/ReVanced/revanced-patcher/commit/bd434ceb3394d1d5292e8b94e5bfd6da0e4e9c72))

# [20.0.0-dev.3](https://github.com/ReVanced/revanced-patcher/compare/v20.0.0-dev.2...v20.0.0-dev.3) (2024-08-01)


### Bug Fixes

* Make constructor internal as supposed ([e95fcd1](https://github.com/ReVanced/revanced-patcher/commit/e95fcd1c0b641164bbf0840ec7e562aeb3bacc3e))


### Features

* Add ability to create options outside of a patch ([b8d763a](https://github.com/ReVanced/revanced-patcher/commit/b8d763a66e0601627dd71c8c24247726aa300146))

# [20.0.0-dev.2](https://github.com/ReVanced/revanced-patcher/compare/v20.0.0-dev.1...v20.0.0-dev.2) (2024-07-31)


### Bug Fixes

* Downgrade smali to fix dex compilation issue ([714447d](https://github.com/ReVanced/revanced-patcher/commit/714447de70096bf736e8e1d31c14bb5f24195070))
* Merge all extensions before initializing lookup maps ([328aa87](https://github.com/ReVanced/revanced-patcher/commit/328aa876d8ed7826be3713754b6404195e9fe84b))
* Use null for compatible package version when adding packages only ([a8e8fa4](https://github.com/ReVanced/revanced-patcher/commit/a8e8fa4093deb8cffbd7a582409f41867f6b568b))

# [20.0.0-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v19.3.1...v20.0.0-dev.1) (2024-07-22)


### Features

* Convert APIs to Kotlin DSL ([#298](https://github.com/ReVanced/revanced-patcher/issues/298)) ([3f9cbd2](https://github.com/ReVanced/revanced-patcher/commit/3f9cbd2408fa085690a062b357e11e42c51e7f8b))


### BREAKING CHANGES

* Various old APIs are removed, and DSL APIs are added instead.

## [19.3.1](https://github.com/ReVanced/revanced-patcher/compare/v19.3.0...v19.3.1) (2024-02-14)

## [19.3.1-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v19.3.0...v19.3.1-dev.1) (2024-02-14)

# [19.3.0](https://github.com/ReVanced/revanced-patcher/compare/v19.2.0...v19.3.0) (2024-02-14)


### Bug Fixes

* Use `Patch#toString` to get patch class name, when no name available ([c9a8260](https://github.com/ReVanced/revanced-patcher/commit/c9a82608f7f2d6b3e64c0c949ea5d9f76fa46165))


### Features

* Read and write arbitrary files in APK files ([f1d7217](https://github.com/ReVanced/revanced-patcher/commit/f1d72174956c42234664dce152a27e6854e347e2))

# [19.3.0-dev.2](https://github.com/ReVanced/revanced-patcher/compare/v19.3.0-dev.1...v19.3.0-dev.2) (2024-02-13)

# [19.3.0-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v19.2.1-dev.1...v19.3.0-dev.1) (2024-02-13)


### Features

* Read and write arbitrary files in APK files ([f1d7217](https://github.com/ReVanced/revanced-patcher/commit/f1d72174956c42234664dce152a27e6854e347e2))

## [19.2.1-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v19.2.0...v19.2.1-dev.1) (2024-01-27)


### Bug Fixes

* Use `Patch#toString` to get patch class name, when no name available ([c9a8260](https://github.com/ReVanced/revanced-patcher/commit/c9a82608f7f2d6b3e64c0c949ea5d9f76fa46165))

# [19.2.0](https://github.com/ReVanced/revanced-patcher/compare/v19.1.0...v19.2.0) (2023-12-28)


### Bug Fixes

* Accept `PatchSet` in `PatchesConsumer#acceptPatches` ([716825f](https://github.com/ReVanced/revanced-patcher/commit/716825f232bf1aab3a97723968562aa6dbdb20b1))


### Features

* Add `PatchExtensions#registerNewPatchOption` function to simplify instantiation and registration of patch options ([4a91845](https://github.com/ReVanced/revanced-patcher/commit/4a9184597be99cd458496cce0ee68994e6b8735c))

# [19.2.0-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v19.1.1-dev.1...v19.2.0-dev.1) (2023-12-22)


### Features

* Add `PatchExtensions#registerNewPatchOption` function to simplify instantiation and registration of patch options ([4a91845](https://github.com/ReVanced/revanced-patcher/commit/4a9184597be99cd458496cce0ee68994e6b8735c))

## [19.1.1-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v19.1.0...v19.1.1-dev.1) (2023-12-01)


### Bug Fixes

* Accept `PatchSet` in `PatchesConsumer#acceptPatches` ([716825f](https://github.com/ReVanced/revanced-patcher/commit/716825f232bf1aab3a97723968562aa6dbdb20b1))

# [19.1.0](https://github.com/ReVanced/revanced-patcher/compare/v19.0.0...v19.1.0) (2023-12-01)


### Features

* Add constructor to initialize patches without annotations ([462fbe2](https://github.com/ReVanced/revanced-patcher/commit/462fbe2cadf56d8b0dde33319256021093bd39d5))
* Retrieve annotations in super and interface classes ([7aeae93](https://github.com/ReVanced/revanced-patcher/commit/7aeae93f3d9a13e294fe1bdb2586f79908af60af))


### Performance Improvements

* Use a hash set for fast lookup ([f1de9b3](https://github.com/ReVanced/revanced-patcher/commit/f1de9b39eff1db44c00acd3e41902b3ec6124776))

# [19.1.0-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v19.0.0...v19.1.0-dev.1) (2023-11-29)


### Features

* Add constructor to initialize patches without annotations ([462fbe2](https://github.com/ReVanced/revanced-patcher/commit/462fbe2cadf56d8b0dde33319256021093bd39d5))
* Retrieve annotations in super and interface classes ([7aeae93](https://github.com/ReVanced/revanced-patcher/commit/7aeae93f3d9a13e294fe1bdb2586f79908af60af))


### Performance Improvements

* Use a hash set for fast lookup ([f1de9b3](https://github.com/ReVanced/revanced-patcher/commit/f1de9b39eff1db44c00acd3e41902b3ec6124776))

# [19.0.0](https://github.com/ReVanced/revanced-patcher/compare/v18.0.0...v19.0.0) (2023-10-24)


### Features

* Add `PatchOption#valueType` to handle type erasure ([a46e948](https://github.com/ReVanced/revanced-patcher/commit/a46e948b5a0cf9bc8d31f557e371cd7d7c2f5b1c))


### BREAKING CHANGES

* This changes the signature of the `PatchOption` constructor.

# [19.0.0-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v18.0.0...v19.0.0-dev.1) (2023-10-24)


### Features

* Add `PatchOption#valueType` to handle type erasure ([a46e948](https://github.com/ReVanced/revanced-patcher/commit/a46e948b5a0cf9bc8d31f557e371cd7d7c2f5b1c))


### BREAKING CHANGES

* This changes the signature of the `PatchOption` constructor.

# [18.0.0](https://github.com/ReVanced/revanced-patcher/compare/v17.0.0...v18.0.0) (2023-10-22)


### Bug Fixes

* Do not set patch fields if they are empty ([a76ac04](https://github.com/ReVanced/revanced-patcher/commit/a76ac04214a2ab91e3b2f9dddb13ed52816fe723))
* Only allow setting `MethodFingerprint#result` privately ([aed1eac](https://github.com/ReVanced/revanced-patcher/commit/aed1eac3157317acf87f522750cf2f41509606c3))


### Code Refactoring

* Change `PatchOption` from abstract to open class ([09cd6aa](https://github.com/ReVanced/revanced-patcher/commit/09cd6aa568988dd5241bfa6a2e12b7926a7b0683))
* Change data classes to actual classes ([6192089](https://github.com/ReVanced/revanced-patcher/commit/6192089b71bdca15765369f3e607ddd1f8266205))
* Convert extension functions to member functions ([e2ca507](https://github.com/ReVanced/revanced-patcher/commit/e2ca50729da7085799c0ff6fc4f7afaf82579738))
* Move files to simplify package structure ([124a2e9](https://github.com/ReVanced/revanced-patcher/commit/124a2e9d3efb88f0f038ae306d941e918ad3ad3c))
* Remove deprecated classes and members ([a4212f6](https://github.com/ReVanced/revanced-patcher/commit/a4212f6bf952971541c4550e20f6bf57a382e19a))


* refactor!: Remove `Fingerprint` interface ([54a2f8f](https://github.com/ReVanced/revanced-patcher/commit/54a2f8f16fddf2b2ed47eb23717ba3734c4a6c5d))


### Features

* Add function to reset options to their default value ([ebbaafb](https://github.com/ReVanced/revanced-patcher/commit/ebbaafb78e88f34faeafe9ff8532afe29231bd79))
* Add function to reset options to their default value ([e6de90d](https://github.com/ReVanced/revanced-patcher/commit/e6de90d300bc9c82ca1696cb898db04c65a1cd5b))
* Add getter for default option value ([c7922e9](https://github.com/ReVanced/revanced-patcher/commit/c7922e90d0c6ae83f513611c706ebea33c1a2b63))
* Make `PatchOption#values` nullable ([56ce9ec](https://github.com/ReVanced/revanced-patcher/commit/56ce9ec2f98ff351c3d42df71b49e5c88f07e665))
* Name patch option value validator property correctly ([caa634f](https://github.com/ReVanced/revanced-patcher/commit/caa634fac6d7a717f54e3b015827c8858fd637b9))
* Remove patch annotation processor ([4456031](https://github.com/ReVanced/revanced-patcher/commit/445603145979a6f67823a79f9d6cd140299cff37))
* Use a map for `PatchOption#values` ([54ac139](https://github.com/ReVanced/revanced-patcher/commit/54ac1394a914d3eed7865ec697e8016834134911))


### Performance Improvements

* Run the garbage collector after writing dex files ([d9fb241](https://github.com/ReVanced/revanced-patcher/commit/d9fb241d57b0c4340130c0e5900250e66730ea56))


### BREAKING CHANGES

* The `MethodFingerprint#result` member can now only be set inside `MethodFingerprint`.
* The `Fingerprint` interface is no longer present.
* Some extension functions are now member functions.
* This gets rid of data class members.
* Some deprecated classes and members are not present anymore.
* Classes and members have changed packages.
* This gets rid of the existing basic implementations of the `PatchOptions` type and moves extension functions.
* This changes the getter name of the property.
* Various patch constructor signatures have changed.

# [18.0.0-dev.6](https://github.com/ReVanced/revanced-patcher/compare/v18.0.0-dev.5...v18.0.0-dev.6) (2023-10-22)


### Bug Fixes

* Only allow setting `MethodFingerprint#result` privately ([aed1eac](https://github.com/ReVanced/revanced-patcher/commit/aed1eac3157317acf87f522750cf2f41509606c3))


### Code Refactoring

* Change data classes to actual classes ([6192089](https://github.com/ReVanced/revanced-patcher/commit/6192089b71bdca15765369f3e607ddd1f8266205))
* Convert extension functions to member functions ([e2ca507](https://github.com/ReVanced/revanced-patcher/commit/e2ca50729da7085799c0ff6fc4f7afaf82579738))
* Move files to simplify package structure ([124a2e9](https://github.com/ReVanced/revanced-patcher/commit/124a2e9d3efb88f0f038ae306d941e918ad3ad3c))
* Remove deprecated classes and members ([a4212f6](https://github.com/ReVanced/revanced-patcher/commit/a4212f6bf952971541c4550e20f6bf57a382e19a))


* refactor!: Remove `Fingerprint` interface ([54a2f8f](https://github.com/ReVanced/revanced-patcher/commit/54a2f8f16fddf2b2ed47eb23717ba3734c4a6c5d))


### BREAKING CHANGES

* The `MethodFingerprint#result` member can now only be set inside `MethodFingerprint`.
* The `Fingerprint` interface is no longer present.
* Some extension functions are now member functions.
* This gets rid of data class members.
* Some deprecated classes and members are not present anymore.
* Classes and members have changed packages.

# [18.0.0-dev.5](https://github.com/ReVanced/revanced-patcher/compare/v18.0.0-dev.4...v18.0.0-dev.5) (2023-10-22)


### Bug Fixes

* Do not set patch fields if they are empty ([a76ac04](https://github.com/ReVanced/revanced-patcher/commit/a76ac04214a2ab91e3b2f9dddb13ed52816fe723))

# [18.0.0-dev.4](https://github.com/ReVanced/revanced-patcher/compare/v18.0.0-dev.3...v18.0.0-dev.4) (2023-10-22)


### Features

* Use a map for `PatchOption#values` ([54ac139](https://github.com/ReVanced/revanced-patcher/commit/54ac1394a914d3eed7865ec697e8016834134911))

# [18.0.0-dev.3](https://github.com/ReVanced/revanced-patcher/compare/v18.0.0-dev.2...v18.0.0-dev.3) (2023-10-22)


### Features

* Make `PatchOption#values` nullable ([56ce9ec](https://github.com/ReVanced/revanced-patcher/commit/56ce9ec2f98ff351c3d42df71b49e5c88f07e665))

# [18.0.0-dev.2](https://github.com/ReVanced/revanced-patcher/compare/v18.0.0-dev.1...v18.0.0-dev.2) (2023-10-22)


### Code Refactoring

* Change `PatchOption` from abstract to open class ([09cd6aa](https://github.com/ReVanced/revanced-patcher/commit/09cd6aa568988dd5241bfa6a2e12b7926a7b0683))


### Features

* Add function to reset options to their default value ([ebbaafb](https://github.com/ReVanced/revanced-patcher/commit/ebbaafb78e88f34faeafe9ff8532afe29231bd79))
* Add function to reset options to their default value ([e6de90d](https://github.com/ReVanced/revanced-patcher/commit/e6de90d300bc9c82ca1696cb898db04c65a1cd5b))
* Add getter for default option value ([c7922e9](https://github.com/ReVanced/revanced-patcher/commit/c7922e90d0c6ae83f513611c706ebea33c1a2b63))
* Name patch option value validator property correctly ([caa634f](https://github.com/ReVanced/revanced-patcher/commit/caa634fac6d7a717f54e3b015827c8858fd637b9))


### BREAKING CHANGES

* This gets rid of the existing basic implementations of the `PatchOptions` type and moves extension functions.
* This changes the getter name of the property.

# [18.0.0-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v17.0.1-dev.1...v18.0.0-dev.1) (2023-10-14)


### Features

* Remove patch annotation processor ([4456031](https://github.com/ReVanced/revanced-patcher/commit/445603145979a6f67823a79f9d6cd140299cff37))


### BREAKING CHANGES

* Various patch constructor signatures have changed.

## [17.0.1-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v17.0.0...v17.0.1-dev.1) (2023-10-10)


### Performance Improvements

* Run the garbage collector after writing dex files ([d9fb241](https://github.com/ReVanced/revanced-patcher/commit/d9fb241d57b0c4340130c0e5900250e66730ea56))

# [17.0.0](https://github.com/ReVanced/revanced-patcher/compare/v16.0.2...v17.0.0) (2023-10-09)


### Features

* Add option to use single threaded writer for dex files ([77dbee3](https://github.com/ReVanced/revanced-patcher/commit/77dbee3d6ae7b8dc77543e036624daa68ae63504))


### BREAKING CHANGES

* This commit gets rid of deprecated constructors.

# [17.0.0-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v16.0.2...v17.0.0-dev.1) (2023-10-09)


### Features

* Add option to use single threaded writer for dex files ([77dbee3](https://github.com/ReVanced/revanced-patcher/commit/77dbee3d6ae7b8dc77543e036624daa68ae63504))


### BREAKING CHANGES

* This commit gets rid of deprecated constructors.

## [16.0.2](https://github.com/ReVanced/revanced-patcher/compare/v16.0.1...v16.0.2) (2023-10-06)


### Performance Improvements

* Use a map to merge integrations classes ([6059d3c](https://github.com/ReVanced/revanced-patcher/commit/6059d3ca2685cb659023b171b95d4b9d279c6e53))

## [16.0.2-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v16.0.1...v16.0.2-dev.1) (2023-10-06)


### Performance Improvements

* Use a map to merge integrations classes ([6059d3c](https://github.com/ReVanced/revanced-patcher/commit/6059d3ca2685cb659023b171b95d4b9d279c6e53))

## [16.0.1](https://github.com/ReVanced/revanced-patcher/compare/v16.0.0...v16.0.1) (2023-10-05)


### Bug Fixes

* Merge integrations when required ([06c2b76](https://github.com/ReVanced/revanced-patcher/commit/06c2b76f11ac1bfe43d51d54d425e7577ecefdf6))

## [16.0.1-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v16.0.0...v16.0.1-dev.1) (2023-10-05)


### Bug Fixes

* Merge integrations when required ([06c2b76](https://github.com/ReVanced/revanced-patcher/commit/06c2b76f11ac1bfe43d51d54d425e7577ecefdf6))

# [16.0.0](https://github.com/ReVanced/revanced-patcher/compare/v15.0.3...v16.0.0) (2023-10-04)


### Bug Fixes

* Use correct super class type ([f590436](https://github.com/ReVanced/revanced-patcher/commit/f590436399f6385c51cea54618251b5d823c31f9))


### BREAKING CHANGES

* This changes the super classes of some `PatchOptionException` classes

# [16.0.0-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v15.0.3...v16.0.0-dev.1) (2023-10-04)


### Bug Fixes

* Use correct super class type ([f590436](https://github.com/ReVanced/revanced-patcher/commit/f590436399f6385c51cea54618251b5d823c31f9))


### BREAKING CHANGES

* This changes the super classes of some `PatchOptionException` classes

## [15.0.3](https://github.com/ReVanced/revanced-patcher/compare/v15.0.2...v15.0.3) (2023-10-01)


### Bug Fixes

* Fix SMALI compilation on devices with RTL language ([#242](https://github.com/ReVanced/revanced-patcher/issues/242)) ([356f1f1](https://github.com/ReVanced/revanced-patcher/commit/356f1f155348347a8f318a2e024716ebf4fec99b))

## [15.0.3-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v15.0.2...v15.0.3-dev.1) (2023-09-29)


### Bug Fixes

* Fix SMALI compilation on devices with RTL language ([#242](https://github.com/ReVanced/revanced-patcher/issues/242)) ([356f1f1](https://github.com/ReVanced/revanced-patcher/commit/356f1f155348347a8f318a2e024716ebf4fec99b))

## [15.0.2](https://github.com/ReVanced/revanced-patcher/compare/v15.0.1...v15.0.2) (2023-09-27)


### Performance Improvements

* Do not unnecessary resolve fingeprints twice ([#241](https://github.com/ReVanced/revanced-patcher/issues/241)) ([4d6e08a](https://github.com/ReVanced/revanced-patcher/commit/4d6e08a650dde6ec2e18611c5db1ab92b9a61dd1))

## [15.0.2-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v15.0.1...v15.0.2-dev.1) (2023-09-26)


### Performance Improvements

* Do not unnecessary resolve fingeprints twice ([#241](https://github.com/ReVanced/revanced-patcher/issues/241)) ([4d6e08a](https://github.com/ReVanced/revanced-patcher/commit/4d6e08a650dde6ec2e18611c5db1ab92b9a61dd1))

## [15.0.1](https://github.com/ReVanced/revanced-patcher/compare/v15.0.0...v15.0.1) (2023-09-20)


### Bug Fixes

* Remove log management ([d51bc32](https://github.com/ReVanced/revanced-patcher/commit/d51bc32e37a865194c3825471085b3ccf8c78421))

## [15.0.1-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v15.0.0...v15.0.1-dev.1) (2023-09-20)


### Bug Fixes

* Remove log management ([d51bc32](https://github.com/ReVanced/revanced-patcher/commit/d51bc32e37a865194c3825471085b3ccf8c78421))

# [15.0.0](https://github.com/ReVanced/revanced-patcher/compare/v14.2.2...v15.0.0) (2023-09-18)


### Bug Fixes

* Account for source patch dependency for tests ([6918418](https://github.com/ReVanced/revanced-patcher/commit/69184187d90f126478d2f49415c1e3381217557f))
* Always make the generated patch depend on the source patch ([8de3063](https://github.com/ReVanced/revanced-patcher/commit/8de30633ae6eb7acf7f0a351e26d4a6c2fdbdfec))
* Catch correct exception ([637d487](https://github.com/ReVanced/revanced-patcher/commit/637d48746ff8694e01c5aead1c75a9a1efeb5ac8))
* Delegate `PatchBundleLoader` by mutable set of patches ([9a109c1](https://github.com/ReVanced/revanced-patcher/commit/9a109c129b135a634be1aad4130a06d9e8e96ecd))
* Do not resolve the proxied patch to the proxy in the dependency list ([e112837](https://github.com/ReVanced/revanced-patcher/commit/e11283744a21fe2d09435e99d6924462b6aac3b8))
* Do not set `CompatiblePackage.versions` if `@CompatiblePackage.versions` is empty ([6b1e0a1](https://github.com/ReVanced/revanced-patcher/commit/6b1e0a16568124e9f82fb5740353360fa8ec614a))
* Filter for patches correctly ([4bc4b0d](https://github.com/ReVanced/revanced-patcher/commit/4bc4b0dc0104073b62528d02a88383cecd7a50e7))
* Find dependency in `context.allPatches` ([670f015](https://github.com/ReVanced/revanced-patcher/commit/670f0153de10c6f0db25b08df1c01a2905037f84))
* Log the correct patch names ([9fdb8f0](https://github.com/ReVanced/revanced-patcher/commit/9fdb8f087f62babf6081879db65c80db639aa0a7))
* Make `CompatiblePackage.versions` a property ([67b7dff](https://github.com/ReVanced/revanced-patcher/commit/67b7dff67a212b4fc30eb4f0cbe58f0ba09fb09a))
* Print patch name instead of class name ([4e7811e](https://github.com/ReVanced/revanced-patcher/commit/4e7811ea07762667a1f22526dc176022038f60eb))
* Print stack trace of exception ([aa71146](https://github.com/ReVanced/revanced-patcher/commit/aa71146b1bf4ffebcc81a1663e15abae89e97ff0))
* Run code-block if `executablePatches` does not yet contain `patch` ([1d7aeca](https://github.com/ReVanced/revanced-patcher/commit/1d7aeca696be873dfaf88eaa6d312949a3b8572b))
* Suppress logger when loading patches in `PatchBundleLoader` ([72c9eb2](https://github.com/ReVanced/revanced-patcher/commit/72c9eb212985f99f3390cf1faa10ab547d2dbe7e))
* Use correct module name ([080fbe9](https://github.com/ReVanced/revanced-patcher/commit/080fbe9feb9d4ea9ec4e599ecef296eacd803b05))


### Code Refactoring

* Internalize processor constructor ([a802d0d](https://github.com/ReVanced/revanced-patcher/commit/a802d0df463695976e85d8391762942eb977920b))


* feat Use `Set` as super type for `PatchBundleLoader` ([4b76d19](https://github.com/ReVanced/revanced-patcher/commit/4b76d1959691babf8c99d3d5235df4a4388956f0))
* feat!: Add patch annotation processor ([3fc6a13](https://github.com/ReVanced/revanced-patcher/commit/3fc6a139eef67237c116fb4e3e29bf9542d3a981))
* feat!: Remove patch annotations ([3b4db3d](https://github.com/ReVanced/revanced-patcher/commit/3b4db3ddb72cdcee8af2f787eadf58eeb37543de))


### Features

* Add patch annotation processor ([#231](https://github.com/ReVanced/revanced-patcher/issues/231)) ([a29931f](https://github.com/ReVanced/revanced-patcher/commit/a29931f2ec0a666dba209b54855425d9dc2f4462))


### BREAKING CHANGES

* This gets rid of the public constructor.
* `PatchBundleLoader` is not a map anymore
* This renames packages and the Maven package.
* The manifest for patches has been removed, and the properties have been added to patches. Patches are now `OptionsContainer`. The `@Patch` annotation has been removed in favour of the `@Patch` annotation from the annotation processor.
* Patch annotations have been removed. PatcherException is now thrown in various places. PatchBundleLoader is now a map of patches associated by their name. Patches are now instances.

# [15.0.0-dev.4](https://github.com/ReVanced/revanced-patcher/compare/v15.0.0-dev.3...v15.0.0-dev.4) (2023-09-13)


### Bug Fixes

* Account for source patch dependency for tests ([6918418](https://github.com/ReVanced/revanced-patcher/commit/69184187d90f126478d2f49415c1e3381217557f))
* Always make the generated patch depend on the source patch ([8de3063](https://github.com/ReVanced/revanced-patcher/commit/8de30633ae6eb7acf7f0a351e26d4a6c2fdbdfec))
* Catch correct exception ([637d487](https://github.com/ReVanced/revanced-patcher/commit/637d48746ff8694e01c5aead1c75a9a1efeb5ac8))
* Delegate `PatchBundleLoader` by mutable set of patches ([9a109c1](https://github.com/ReVanced/revanced-patcher/commit/9a109c129b135a634be1aad4130a06d9e8e96ecd))
* Do not resolve the proxied patch to the proxy in the dependency list ([e112837](https://github.com/ReVanced/revanced-patcher/commit/e11283744a21fe2d09435e99d6924462b6aac3b8))
* Do not set `CompatiblePackage.versions` if `@CompatiblePackage.versions` is empty ([6b1e0a1](https://github.com/ReVanced/revanced-patcher/commit/6b1e0a16568124e9f82fb5740353360fa8ec614a))
* Filter for patches correctly ([4bc4b0d](https://github.com/ReVanced/revanced-patcher/commit/4bc4b0dc0104073b62528d02a88383cecd7a50e7))
* Find dependency in `context.allPatches` ([670f015](https://github.com/ReVanced/revanced-patcher/commit/670f0153de10c6f0db25b08df1c01a2905037f84))
* Log the correct patch names ([9fdb8f0](https://github.com/ReVanced/revanced-patcher/commit/9fdb8f087f62babf6081879db65c80db639aa0a7))
* Print patch name instead of class name ([4e7811e](https://github.com/ReVanced/revanced-patcher/commit/4e7811ea07762667a1f22526dc176022038f60eb))
* Print stack trace of exception ([aa71146](https://github.com/ReVanced/revanced-patcher/commit/aa71146b1bf4ffebcc81a1663e15abae89e97ff0))
* Run code-block if `executablePatches` does not yet contain `patch` ([1d7aeca](https://github.com/ReVanced/revanced-patcher/commit/1d7aeca696be873dfaf88eaa6d312949a3b8572b))
* Suppress logger when loading patches in `PatchBundleLoader` ([72c9eb2](https://github.com/ReVanced/revanced-patcher/commit/72c9eb212985f99f3390cf1faa10ab547d2dbe7e))


### Code Refactoring

* Internalize processor constructor ([a802d0d](https://github.com/ReVanced/revanced-patcher/commit/a802d0df463695976e85d8391762942eb977920b))


### BREAKING CHANGES

* This gets rid of the public constructor.

# [15.0.0-dev.3](https://github.com/ReVanced/revanced-patcher/compare/v15.0.0-dev.2...v15.0.0-dev.3) (2023-09-06)


### Bug Fixes

* Make `CompatiblePackage.versions` a property ([67b7dff](https://github.com/ReVanced/revanced-patcher/commit/67b7dff67a212b4fc30eb4f0cbe58f0ba09fb09a))
* Use correct module name ([080fbe9](https://github.com/ReVanced/revanced-patcher/commit/080fbe9feb9d4ea9ec4e599ecef296eacd803b05))


* feat Use `Set` as super type for `PatchBundleLoader` ([4b76d19](https://github.com/ReVanced/revanced-patcher/commit/4b76d1959691babf8c99d3d5235df4a4388956f0))


### BREAKING CHANGES

* `PatchBundleLoader` is not a map anymore
* This renames packages and the Maven package.

# [15.0.0-dev.2](https://github.com/ReVanced/revanced-patcher/compare/v15.0.0-dev.1...v15.0.0-dev.2) (2023-09-06)

# [15.0.0-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v14.2.2...v15.0.0-dev.1) (2023-09-04)


* feat!: Add patch annotation processor ([3fc6a13](https://github.com/ReVanced/revanced-patcher/commit/3fc6a139eef67237c116fb4e3e29bf9542d3a981))
* feat!: Remove patch annotations ([3b4db3d](https://github.com/ReVanced/revanced-patcher/commit/3b4db3ddb72cdcee8af2f787eadf58eeb37543de))


### Features

* Add patch annotation processor ([#231](https://github.com/ReVanced/revanced-patcher/issues/231)) ([a29931f](https://github.com/ReVanced/revanced-patcher/commit/a29931f2ec0a666dba209b54855425d9dc2f4462))


### BREAKING CHANGES

* The manifest for patches has been removed, and the properties have been added to patches. Patches are now `OptionsContainer`. The `@Patch` annotation has been removed in favour of the `@Patch` annotation from the annotation processor.
* Patch annotations have been removed. PatcherException is now thrown in various places. PatchBundleLoader is now a map of patches associated by their name. Patches are now instances.

## [14.2.2](https://github.com/ReVanced/revanced-patcher/compare/v14.2.1...v14.2.2) (2023-08-30)


### Bug Fixes

* allow setting `DexClassLoader.optimizedDirectory` ([11a3378](https://github.com/ReVanced/revanced-patcher/commit/11a337865947a6ac74a63ebb3f3f9bc2610f7771))

## [14.2.2-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v14.2.1...v14.2.2-dev.1) (2023-08-29)


### Bug Fixes

* allow setting `DexClassLoader.optimizedDirectory` ([11a3378](https://github.com/ReVanced/revanced-patcher/commit/11a337865947a6ac74a63ebb3f3f9bc2610f7771))

## [14.2.1](https://github.com/ReVanced/revanced-patcher/compare/v14.2.0...v14.2.1) (2023-08-27)


### Bug Fixes

* do not flag resource table as sparse when main package is not loaded ([b832812](https://github.com/ReVanced/revanced-patcher/commit/b832812767a06ec6ec232291e6d14c8c2f14118c))

## [14.2.1-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v14.2.0...v14.2.1-dev.1) (2023-08-27)


### Bug Fixes

* do not flag resource table as sparse when main package is not loaded ([b832812](https://github.com/ReVanced/revanced-patcher/commit/b832812767a06ec6ec232291e6d14c8c2f14118c))

# [14.2.0](https://github.com/ReVanced/revanced-patcher/compare/v14.1.0...v14.2.0) (2023-08-27)


### Features

* load patches in lexicographical order ([e8f2087](https://github.com/ReVanced/revanced-patcher/commit/e8f2087a6ffa6077fb3a6a69e29f3aec72e2fc1b))
* log when merging integrations ([983563e](https://github.com/ReVanced/revanced-patcher/commit/983563efb6d7c8d289464b8bf71a016b8a735630))


### Performance Improvements

* compare types of classes ([55d6945](https://github.com/ReVanced/revanced-patcher/commit/55d694579ac2718b9e2c61ca5f38419c3775ef87))

# [14.2.0-dev.3](https://github.com/ReVanced/revanced-patcher/compare/v14.2.0-dev.2...v14.2.0-dev.3) (2023-08-26)


### Performance Improvements

* compare types of classes ([55d6945](https://github.com/ReVanced/revanced-patcher/commit/55d694579ac2718b9e2c61ca5f38419c3775ef87))

# [14.2.0-dev.2](https://github.com/ReVanced/revanced-patcher/compare/v14.2.0-dev.1...v14.2.0-dev.2) (2023-08-26)


### Features

* log when merging integrations ([983563e](https://github.com/ReVanced/revanced-patcher/commit/983563efb6d7c8d289464b8bf71a016b8a735630))

# [14.2.0-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v14.1.0...v14.2.0-dev.1) (2023-08-25)


### Features

* load patches in lexicographical order ([e8f2087](https://github.com/ReVanced/revanced-patcher/commit/e8f2087a6ffa6077fb3a6a69e29f3aec72e2fc1b))

# [14.1.0](https://github.com/ReVanced/revanced-patcher/compare/v14.0.0...v14.1.0) (2023-08-24)


### Bug Fixes

* move version properties file to correct package ([e985676](https://github.com/ReVanced/revanced-patcher/commit/e985676c2d8e5d6cb907d371de30428caaa6da43))


### Features

* properly make use of logging facade ([ba56a6a](https://github.com/ReVanced/revanced-patcher/commit/ba56a6a2eef503c0d6cdd846ddce2e1474d8ed1a))

# [14.1.0-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v14.0.1-dev.1...v14.1.0-dev.1) (2023-08-24)


### Features

* properly make use of logging facade ([ba56a6a](https://github.com/ReVanced/revanced-patcher/commit/ba56a6a2eef503c0d6cdd846ddce2e1474d8ed1a))

## [14.0.1-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v14.0.0...v14.0.1-dev.1) (2023-08-23)


### Bug Fixes

* move version properties file to correct package ([e985676](https://github.com/ReVanced/revanced-patcher/commit/e985676c2d8e5d6cb907d371de30428caaa6da43))

# [14.0.0](https://github.com/ReVanced/revanced-patcher/compare/v13.0.0...v14.0.0) (2023-08-22)


### Bug Fixes

* log decoding resources after logging deleting resource cache directory ([db62a16](https://github.com/ReVanced/revanced-patcher/commit/db62a1607b4a9d6256b5f5153decb088d9680553))
* only emit closed patches that did not throw an exception with the `@Patch` annotation ([5938f6b](https://github.com/ReVanced/revanced-patcher/commit/5938f6b7ea25103a0a1b56ceebe49139bc80c6f5))
* supply the parent classloader to `DexClassLoader` ([0f15077](https://github.com/ReVanced/revanced-patcher/commit/0f15077225600b65200022c1a318e504deb472b9))


### Code Refactoring

* improve structure and public API ([6b8977f](https://github.com/ReVanced/revanced-patcher/commit/6b8977f17854ef0344d868e6391cb18134eceadc))


### Features

* do not log instantiation of ReVanced Patcher ([273dd8d](https://github.com/ReVanced/revanced-patcher/commit/273dd8d388f8e9b7436c6d6145a94c12c1fabe55))


### BREAKING CHANGES

* Various public APIs have been changed. The `Version` annotation has been removed. Patches do not return anything anymore and instead throw `PatchException`. Multiple patch bundles can now be loaded in a single ClassLoader to bypass class loader isolation.

# [14.0.0-dev.4](https://github.com/ReVanced/revanced-patcher/compare/v14.0.0-dev.3...v14.0.0-dev.4) (2023-08-22)


### Bug Fixes

* only emit closed patches that did not throw an exception with the `@Patch` annotation ([5938f6b](https://github.com/ReVanced/revanced-patcher/commit/5938f6b7ea25103a0a1b56ceebe49139bc80c6f5))

# [14.0.0-dev.3](https://github.com/ReVanced/revanced-patcher/compare/v14.0.0-dev.2...v14.0.0-dev.3) (2023-08-20)


### Bug Fixes

* supply the parent classloader to `DexClassLoader` ([0f15077](https://github.com/ReVanced/revanced-patcher/commit/0f15077225600b65200022c1a318e504deb472b9))


### Features

* do not log instantiation of ReVanced Patcher ([273dd8d](https://github.com/ReVanced/revanced-patcher/commit/273dd8d388f8e9b7436c6d6145a94c12c1fabe55))

# [14.0.0-dev.2](https://github.com/ReVanced/revanced-patcher/compare/v14.0.0-dev.1...v14.0.0-dev.2) (2023-08-19)

# [14.0.0-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v13.0.0...v14.0.0-dev.1) (2023-08-18)


### Bug Fixes

* log decoding resources after logging deleting resource cache directory ([db62a16](https://github.com/ReVanced/revanced-patcher/commit/db62a1607b4a9d6256b5f5153decb088d9680553))


### Code Refactoring

* improve structure and public API ([6b8977f](https://github.com/ReVanced/revanced-patcher/commit/6b8977f17854ef0344d868e6391cb18134eceadc))


### BREAKING CHANGES

* Various public APIs have been changed. The `Version` annotation has been removed. Patches do not return anything anymore and instead throw `PatchException`. Multiple patch bundles can now be loaded in a single ClassLoader to bypass class loader isolation.

# [13.0.0](https://github.com/ReVanced/revanced-patcher/compare/v12.1.1...v13.0.0) (2023-08-14)


### Bug Fixes

* decode in correct order ([8fb2f2d](https://github.com/ReVanced/revanced-patcher/commit/8fb2f2dc1d3b9b1e9fd13b39485985d2886d52ae))
* disable correct loggers ([c2d89c6](https://github.com/ReVanced/revanced-patcher/commit/c2d89c622e06e58e5042e1a00ef67cee8a246e53))
* get framework ids to compile resources ([f2cb7ee](https://github.com/ReVanced/revanced-patcher/commit/f2cb7ee7dffa573c31df497cf235a3f5d120f91f))
* only enable logging for ReVanced ([783ccf8](https://github.com/ReVanced/revanced-patcher/commit/783ccf8529f5d16aa463982da6977328306232bb))
* set package metadata correctly ([02d6ff1](https://github.com/ReVanced/revanced-patcher/commit/02d6ff15fe87c2352de29749610e9d72db8ba418))


* build(Needs bump)!: Bump dependencies ([d5f89a9](https://github.com/ReVanced/revanced-patcher/commit/d5f89a903f019c199bdb27a50287124fc4b4978e))


### BREAKING CHANGES

* This bump updates smali, a crucial dependency

# [13.0.0-dev.3](https://github.com/ReVanced/revanced-patcher/compare/v13.0.0-dev.2...v13.0.0-dev.3) (2023-08-14)


### Bug Fixes

* decode in correct order ([8fb2f2d](https://github.com/ReVanced/revanced-patcher/commit/8fb2f2dc1d3b9b1e9fd13b39485985d2886d52ae))
* only enable logging for ReVanced ([783ccf8](https://github.com/ReVanced/revanced-patcher/commit/783ccf8529f5d16aa463982da6977328306232bb))

# [13.0.0-dev.2](https://github.com/ReVanced/revanced-patcher/compare/v13.0.0-dev.1...v13.0.0-dev.2) (2023-08-12)


### Bug Fixes

* disable correct loggers ([c2d89c6](https://github.com/ReVanced/revanced-patcher/commit/c2d89c622e06e58e5042e1a00ef67cee8a246e53))
* get framework ids to compile resources ([f2cb7ee](https://github.com/ReVanced/revanced-patcher/commit/f2cb7ee7dffa573c31df497cf235a3f5d120f91f))
* set package metadata correctly ([02d6ff1](https://github.com/ReVanced/revanced-patcher/commit/02d6ff15fe87c2352de29749610e9d72db8ba418))

# [13.0.0-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v12.1.1...v13.0.0-dev.1) (2023-08-11)


* build(Needs bump)!: Bump dependencies ([d5f89a9](https://github.com/ReVanced/revanced-patcher/commit/d5f89a903f019c199bdb27a50287124fc4b4978e))


### BREAKING CHANGES

* This bump updates smali, a crucial dependency

## [12.1.1](https://github.com/ReVanced/revanced-patcher/compare/v12.1.0...v12.1.1) (2023-08-03)


### Bug Fixes

* clear method lookup maps before initializing them ([#210](https://github.com/ReVanced/revanced-patcher/issues/210)) ([746544f](https://github.com/ReVanced/revanced-patcher/commit/746544f9d51d1013bb160075709cd26bffd425b3))

## [12.1.1-dev.2](https://github.com/ReVanced/revanced-patcher/compare/v12.1.1-dev.1...v12.1.1-dev.2) (2023-08-03)

## [12.1.1-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v12.1.0...v12.1.1-dev.1) (2023-08-03)


### Bug Fixes

* clear method lookup maps before initializing them ([#210](https://github.com/ReVanced/revanced-patcher/issues/210)) ([746544f](https://github.com/ReVanced/revanced-patcher/commit/746544f9d51d1013bb160075709cd26bffd425b3))

# [12.1.0](https://github.com/ReVanced/revanced-patcher/compare/v12.0.0...v12.1.0) (2023-08-03)


### Features

* add `MutableMethod.getInstructions` extension function ([fae4029](https://github.com/ReVanced/revanced-patcher/commit/fae4029cfccfad7aa3dd8f7fbef1c63ee26b85b3))

# [12.1.0-dev.2](https://github.com/ReVanced/revanced-patcher/compare/v12.1.0-dev.1...v12.1.0-dev.2) (2023-08-03)

# [12.1.0-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v12.0.0...v12.1.0-dev.1) (2023-08-01)


### Features

* add `MutableMethod.getInstructions` extension function ([fae4029](https://github.com/ReVanced/revanced-patcher/commit/fae4029cfccfad7aa3dd8f7fbef1c63ee26b85b3))

# [12.0.0](https://github.com/ReVanced/revanced-patcher/compare/v11.0.4...v12.0.0) (2023-07-30)


### Bug Fixes

* correct access flags of `PackageMetadata` ([416d691](https://github.com/ReVanced/revanced-patcher/commit/416d69142f50dab49c9ea3f027e9d53e4777f257))
* set resource table via resource decoder ([e0f8e1b](https://github.com/ReVanced/revanced-patcher/commit/e0f8e1b71a295948b610029c89a48f52762396b6))


### Features

* Deprecate `Version` annotation ([c9bbcf2](https://github.com/ReVanced/revanced-patcher/commit/c9bbcf2bf2b0f50ab9100380a3a66c6346ad42ac))
* remove `Path` option ([#202](https://github.com/ReVanced/revanced-patcher/issues/202)) ([69e4a49](https://github.com/ReVanced/revanced-patcher/commit/69e4a490659ebc4fb4bf46148634f4b064ef1713))


### BREAKING CHANGES

* This removes the previously available `Path` option

# [12.0.0-dev.2](https://github.com/ReVanced/revanced-patcher/compare/v12.0.0-dev.1...v12.0.0-dev.2) (2023-07-28)


### Features

* Deprecate `Version` annotation ([400442f](https://github.com/ReVanced/revanced-patcher/commit/400442f70ee56cafd4493b2ce64a294db9836509))

# [12.0.0-dev.1](https://github.com/ReVanced/revanced-patcher/compare/v11.0.4...v12.0.0-dev.1) (2023-07-26)


### Bug Fixes

* correct access flags of `PackageMetadata` ([416d691](https://github.com/ReVanced/revanced-patcher/commit/416d69142f50dab49c9ea3f027e9d53e4777f257))
* set resource table via resource decoder ([e0f8e1b](https://github.com/ReVanced/revanced-patcher/commit/e0f8e1b71a295948b610029c89a48f52762396b6))


### Features

* remove `Path` option ([#202](https://github.com/ReVanced/revanced-patcher/issues/202)) ([69e4a49](https://github.com/ReVanced/revanced-patcher/commit/69e4a490659ebc4fb4bf46148634f4b064ef1713))


### BREAKING CHANGES

* This removes the previously available `Path` option

## [11.0.4](https://github.com/revanced/revanced-patcher/compare/v11.0.3...v11.0.4) (2023-07-01)


### Bug Fixes

* clear method lookup maps ([#198](https://github.com/revanced/revanced-patcher/issues/198)) ([9d81baf](https://github.com/revanced/revanced-patcher/commit/9d81baf4b4ca7514f8a1009e72218638609a7c7f))

## [11.0.4-dev.1](https://github.com/revanced/revanced-patcher/compare/v11.0.3...v11.0.4-dev.1) (2023-07-01)


### Bug Fixes

* clear method lookup maps ([#198](https://github.com/revanced/revanced-patcher/issues/198)) ([9d81baf](https://github.com/revanced/revanced-patcher/commit/9d81baf4b4ca7514f8a1009e72218638609a7c7f))

## [11.0.3](https://github.com/revanced/revanced-patcher/compare/v11.0.2...v11.0.3) (2023-06-30)


### Bug Fixes

* NPE on method lookup ([#195](https://github.com/revanced/revanced-patcher/issues/195)) ([fcef434](https://github.com/revanced/revanced-patcher/commit/fcef4342e8bde73945e8315aef6337cc8a8d8572))

## [11.0.3-dev.1](https://github.com/revanced/revanced-patcher/compare/v11.0.2...v11.0.3-dev.1) (2023-06-28)


### Bug Fixes

* NPE on method lookup ([#195](https://github.com/revanced/revanced-patcher/issues/195)) ([fcef434](https://github.com/revanced/revanced-patcher/commit/fcef4342e8bde73945e8315aef6337cc8a8d8572))

## [11.0.2](https://github.com/revanced/revanced-patcher/compare/v11.0.1...v11.0.2) (2023-06-27)


### Bug Fixes

* catch exceptions from closing patches ([d5d6f85](https://github.com/revanced/revanced-patcher/commit/d5d6f85084c03ed9c776632823ca12394a716167))
* do not load annotations as patches ([519359a](https://github.com/revanced/revanced-patcher/commit/519359a9eb0e9dfa390c5016e9fe4a7490b8ab18))
* only close succeeded patches ([b8151eb](https://github.com/revanced/revanced-patcher/commit/b8151ebccb5b27dd9e06fa63235cf9baeef1c0ee))
* use `versionCode` if `versionName` is unavailable ([6e1b647](https://github.com/revanced/revanced-patcher/commit/6e1b6479b677657c226693e9cc6b63f4ef2ee060))


### Performance Improvements

* resolve fingerprints using method maps ([#185](https://github.com/revanced/revanced-patcher/issues/185)) ([d718134](https://github.com/revanced/revanced-patcher/commit/d718134ab26423e02708e01eba711737f9260ba0))

## [11.0.2-dev.4](https://github.com/revanced/revanced-patcher/compare/v11.0.2-dev.3...v11.0.2-dev.4) (2023-06-27)


### Bug Fixes

* do not load annotations as patches ([519359a](https://github.com/revanced/revanced-patcher/commit/519359a9eb0e9dfa390c5016e9fe4a7490b8ab18))

## [11.0.2-dev.3](https://github.com/revanced/revanced-patcher/compare/v11.0.2-dev.2...v11.0.2-dev.3) (2023-06-27)


### Performance Improvements

* resolve fingerprints using method maps ([#185](https://github.com/revanced/revanced-patcher/issues/185)) ([d718134](https://github.com/revanced/revanced-patcher/commit/d718134ab26423e02708e01eba711737f9260ba0))

## [11.0.2-dev.2](https://github.com/revanced/revanced-patcher/compare/v11.0.2-dev.1...v11.0.2-dev.2) (2023-06-18)


### Bug Fixes

* use `versionCode` if `versionName` is unavailable ([6e1b647](https://github.com/revanced/revanced-patcher/commit/6e1b6479b677657c226693e9cc6b63f4ef2ee060))

## [11.0.2-dev.1](https://github.com/revanced/revanced-patcher/compare/v11.0.1...v11.0.2-dev.1) (2023-06-14)


### Bug Fixes

* catch exceptions from closing patches ([d5d6f85](https://github.com/revanced/revanced-patcher/commit/d5d6f85084c03ed9c776632823ca12394a716167))
* only close succeeded patches ([b8151eb](https://github.com/revanced/revanced-patcher/commit/b8151ebccb5b27dd9e06fa63235cf9baeef1c0ee))

## [11.0.1](https://github.com/revanced/revanced-patcher/compare/v11.0.0...v11.0.1) (2023-06-12)


### Bug Fixes

* revert using `OutputStream.nullOutputStream` ([f02a426](https://github.com/revanced/revanced-patcher/commit/f02a42610b7698fc8cc6bc5183c9ccba2ed96cda))

## [11.0.1-dev.1](https://github.com/revanced/revanced-patcher/compare/v11.0.0...v11.0.1-dev.1) (2023-06-12)


### Bug Fixes

* revert using `OutputStream.nullOutputStream` ([f02a426](https://github.com/revanced/revanced-patcher/commit/f02a42610b7698fc8cc6bc5183c9ccba2ed96cda))

# [11.0.0](https://github.com/revanced/revanced-patcher/compare/v10.0.0...v11.0.0) (2023-06-10)


### Bug Fixes

* add imports to fix failing tests ([43d6868](https://github.com/revanced/revanced-patcher/commit/43d6868d1f59922f9812f3e4a2a890f3b331def6))


* refactor!: move extension functions to their corresponding classes ([a12fe7d](https://github.com/revanced/revanced-patcher/commit/a12fe7dd9e976c38a0a82fe35e6650f58f815de4))
* refactor!: use proper extension function names ([efdd01a](https://github.com/revanced/revanced-patcher/commit/efdd01a9886b6f06af213731824621964367b2a3))
* fix!: implement extension functions consistently ([aacf900](https://github.com/revanced/revanced-patcher/commit/aacf9007647b1cc11bc40053625802573efda6ef))


### BREAKING CHANGES

* This changes the import paths for extension functions.
* This changes the names of extension functions
* This changes the name of functions

# [11.0.0-dev.2](https://github.com/revanced/revanced-patcher/compare/v11.0.0-dev.1...v11.0.0-dev.2) (2023-06-09)


### Bug Fixes

* add imports to fix failing tests ([43d6868](https://github.com/revanced/revanced-patcher/commit/43d6868d1f59922f9812f3e4a2a890f3b331def6))

# [11.0.0-dev.1](https://github.com/revanced/revanced-patcher/compare/v10.0.0...v11.0.0-dev.1) (2023-06-07)


* refactor!: move extension functions to their corresponding classes ([a12fe7d](https://github.com/revanced/revanced-patcher/commit/a12fe7dd9e976c38a0a82fe35e6650f58f815de4))
* refactor!: use proper extension function names ([efdd01a](https://github.com/revanced/revanced-patcher/commit/efdd01a9886b6f06af213731824621964367b2a3))
* fix!: implement extension functions consistently ([aacf900](https://github.com/revanced/revanced-patcher/commit/aacf9007647b1cc11bc40053625802573efda6ef))


### BREAKING CHANGES

* This changes the import paths for extension functions.
* This changes the names of extension functions
* This changes the name of functions

# [10.0.0](https://github.com/revanced/revanced-patcher/compare/v9.0.0...v10.0.0) (2023-06-07)


* fix!: check for two methods parameters orders (#183) ([b6d6a75](https://github.com/revanced/revanced-patcher/commit/b6d6a7591ba1c0b48ffdef52352709564da8d9be)), closes [#183](https://github.com/revanced/revanced-patcher/issues/183)


### BREAKING CHANGES

* This requires changes to `MethodFingerprint`

# [10.0.0-dev.1](https://github.com/revanced/revanced-patcher/compare/v9.0.0...v10.0.0-dev.1) (2023-06-07)


* fix!: check for two methods parameters orders (#183) ([b6d6a75](https://github.com/revanced/revanced-patcher/commit/b6d6a7591ba1c0b48ffdef52352709564da8d9be)), closes [#183](https://github.com/revanced/revanced-patcher/issues/183)


### BREAKING CHANGES

* This requires changes to `MethodFingerprint`

# [9.0.0](https://github.com/revanced/revanced-patcher/compare/v8.0.0...v9.0.0) (2023-05-23)


* refactor!: rename parameter ([526a3d7](https://github.com/revanced/revanced-patcher/commit/526a3d7c359e2d95d26756da0f88d5ce975f5d9b))


### BREAKING CHANGES

* This changes named parameters.

# [9.0.0-dev.1](https://github.com/revanced/revanced-patcher/compare/v8.0.0...v9.0.0-dev.1) (2023-05-23)


* refactor!: rename parameter ([526a3d7](https://github.com/revanced/revanced-patcher/commit/526a3d7c359e2d95d26756da0f88d5ce975f5d9b))


### BREAKING CHANGES

* This changes named parameters.

# [8.0.0](https://github.com/revanced/revanced-patcher/compare/v7.1.1...v8.0.0) (2023-05-13)


* feat!: add `classDef` parameter to `MethodFingerprint` (#175) ([a205220](https://github.com/revanced/revanced-patcher/commit/a2052202b23037150df6aadc47f6e91efcd481cf)), closes [#175](https://github.com/revanced/revanced-patcher/issues/175)


### BREAKING CHANGES

* This changes the signature of the `customFingerprint` function.

# [8.0.0-dev.1](https://github.com/revanced/revanced-patcher/compare/v7.1.1...v8.0.0-dev.1) (2023-05-10)


* feat!: add `classDef` parameter to `MethodFingerprint` (#175) ([a205220](https://github.com/revanced/revanced-patcher/commit/a2052202b23037150df6aadc47f6e91efcd481cf)), closes [#175](https://github.com/revanced/revanced-patcher/issues/175)


### BREAKING CHANGES

* This changes the signature of the `customFingerprint` function.

## [7.1.1](https://github.com/revanced/revanced-patcher/compare/v7.1.0...v7.1.1) (2023-05-07)


### Bug Fixes

* remove `count` instead of `count + 1` instructions with `removeInstructions` ([#167](https://github.com/revanced/revanced-patcher/issues/167)) ([98f8eed](https://github.com/revanced/revanced-patcher/commit/98f8eedecd72b0afe6a0f099a3641a1cc6be2698))

## [7.1.1-dev.1](https://github.com/revanced/revanced-patcher/compare/v7.1.0...v7.1.1-dev.1) (2023-05-07)


### Bug Fixes

* remove `count` instead of `count + 1` instructions with `removeInstructions` ([#167](https://github.com/revanced/revanced-patcher/issues/167)) ([98f8eed](https://github.com/revanced/revanced-patcher/commit/98f8eedecd72b0afe6a0f099a3641a1cc6be2698))

# [7.1.0](https://github.com/revanced/revanced-patcher/compare/v7.0.0...v7.1.0) (2023-05-05)


### Features

* add appreciation message for new contributors ([d674362](https://github.com/revanced/revanced-patcher/commit/d67436271ddca9ccfe008272c1ca82c6123ae7ee))
* add overload to get instruction as type ([49c173d](https://github.com/revanced/revanced-patcher/commit/49c173dc14137ddd198a611e9882dc71300831ee))

# [7.1.0-dev.2](https://github.com/revanced/revanced-patcher/compare/v7.1.0-dev.1...v7.1.0-dev.2) (2023-05-05)


### Features

* add overload to get instruction as type ([49c173d](https://github.com/revanced/revanced-patcher/commit/49c173dc14137ddd198a611e9882dc71300831ee))

# [7.1.0-dev.1](https://github.com/revanced/revanced-patcher/compare/v7.0.0...v7.1.0-dev.1) (2023-04-30)


### Features

* add appreciation message for new contributors ([d674362](https://github.com/revanced/revanced-patcher/commit/d67436271ddca9ccfe008272c1ca82c6123ae7ee))

# [7.0.0](https://github.com/revanced/revanced-patcher/compare/v6.4.3...v7.0.0) (2023-02-26)


* feat!: merge integrations only when necessary ([6e24a85](https://github.com/revanced/revanced-patcher/commit/6e24a85eabd1e7a1484fad229d5ba55c3ba1f1b4))


### BREAKING CHANGES

* `Patcher.addFiles` is now renamed to `Patcher.addIntegrations`

Signed-off-by: oSumAtrIX <johan.melkonyan1@web.de>

# [7.0.0-dev.1](https://github.com/revanced/revanced-patcher/compare/v6.4.3...v7.0.0-dev.1) (2023-02-26)


* feat!: merge integrations only when necessary ([6e24a85](https://github.com/revanced/revanced-patcher/commit/6e24a85eabd1e7a1484fad229d5ba55c3ba1f1b4))


### BREAKING CHANGES

* `Patcher.addFiles` is now renamed to `Patcher.addIntegrations`

Signed-off-by: oSumAtrIX <johan.melkonyan1@web.de>

## [6.4.3](https://github.com/revanced/revanced-patcher/compare/v6.4.2...v6.4.3) (2023-02-10)


### Bug Fixes

* check `CONST_STRING_JUMP` instructions for matching string ([058d292](https://github.com/revanced/revanced-patcher/commit/058d292ad5e297f4c652ff543c13e77a39f7fb1b))

## [6.4.3-dev.1](https://github.com/revanced/revanced-patcher/compare/v6.4.2...v6.4.3-dev.1) (2023-02-10)


### Bug Fixes

* check `CONST_STRING_JUMP` instructions for matching string ([058d292](https://github.com/revanced/revanced-patcher/commit/058d292ad5e297f4c652ff543c13e77a39f7fb1b))

## [6.4.2](https://github.com/revanced/revanced-patcher/compare/v6.4.1...v6.4.2) (2023-01-17)


### Bug Fixes

* resolve failing builds ([a263fdf](https://github.com/revanced/revanced-patcher/commit/a263fdfd413fc05098e28d4800e36ce7d313085b))

## [6.4.2-dev.1](https://github.com/revanced/revanced-patcher/compare/v6.4.1...v6.4.2-dev.1) (2023-01-17)


### Bug Fixes

* resolve failing builds ([a263fdf](https://github.com/revanced/revanced-patcher/commit/a263fdfd413fc05098e28d4800e36ce7d313085b))

## [6.4.1](https://github.com/revanced/revanced-patcher/compare/v6.4.0...v6.4.1) (2023-01-15)


### Bug Fixes

* update dependency `app.revanced:multidexlib2` ([#150](https://github.com/revanced/revanced-patcher/issues/150)) ([dd7dd38](https://github.com/revanced/revanced-patcher/commit/dd7dd383577dcfc95e97f77b446a89b41b589dc0))

## [6.4.1](https://github.com/revanced/revanced-patcher/compare/v6.4.0...v6.4.1) (2023-01-15)


### Bug Fixes

* update dependency `app.revanced:multidexlib2` ([#150](https://github.com/revanced/revanced-patcher/issues/150)) ([dd7dd38](https://github.com/revanced/revanced-patcher/commit/dd7dd383577dcfc95e97f77b446a89b41b589dc0))

## [6.4.1-dev.1](https://github.com/revanced/revanced-patcher/compare/v6.4.0...v6.4.1-dev.1) (2023-01-15)


### Bug Fixes

* update dependency `app.revanced:multidexlib2` ([#150](https://github.com/revanced/revanced-patcher/issues/150)) ([dd7dd38](https://github.com/revanced/revanced-patcher/commit/dd7dd383577dcfc95e97f77b446a89b41b589dc0))

# [6.4.0](https://github.com/revanced/revanced-patcher/compare/v6.3.2...v6.4.0) (2023-01-02)


### Features

* add missing setter to `MutableMethod` ([8f3ecc3](https://github.com/revanced/revanced-patcher/commit/8f3ecc318c39f0270aff53efdee7a1c8d82af421))
* do not fix methods or methods in class merger ([4102f43](https://github.com/revanced/revanced-patcher/commit/4102f43b8a9473fd0ee96c5d4fb8f6e9b4e30e70))
* fix method and field access when merging classes ([5c09ef7](https://github.com/revanced/revanced-patcher/commit/5c09ef7837f9b731e137b66c19da77f63c007595))
* make `aaptPath` nullable ([#146](https://github.com/revanced/revanced-patcher/issues/146)) ([9f0a09a](https://github.com/revanced/revanced-patcher/commit/9f0a09a7569fd5dd78afa27cb66a73d1662edc69))

# [6.4.0-dev.2](https://github.com/revanced/revanced-patcher/compare/v6.4.0-dev.1...v6.4.0-dev.2) (2023-01-02)


### Features

* add missing setter to `MutableMethod` ([8f3ecc3](https://github.com/revanced/revanced-patcher/commit/8f3ecc318c39f0270aff53efdee7a1c8d82af421))
* do not fix methods or methods in class merger ([4102f43](https://github.com/revanced/revanced-patcher/commit/4102f43b8a9473fd0ee96c5d4fb8f6e9b4e30e70))
* fix method and field access when merging classes ([5c09ef7](https://github.com/revanced/revanced-patcher/commit/5c09ef7837f9b731e137b66c19da77f63c007595))

# [6.4.0-dev.1](https://github.com/revanced/revanced-patcher/compare/v6.3.2...v6.4.0-dev.1) (2022-12-20)


### Features

* make `aaptPath` nullable ([#146](https://github.com/revanced/revanced-patcher/issues/146)) ([9f0a09a](https://github.com/revanced/revanced-patcher/commit/9f0a09a7569fd5dd78afa27cb66a73d1662edc69))

## [6.3.2](https://github.com/revanced/revanced-patcher/compare/v6.3.1...v6.3.2) (2022-12-18)


### Bug Fixes

* check if fingerprint string is substring of any string references ([c5de9e2](https://github.com/revanced/revanced-patcher/commit/c5de9e29889dffd18b31e62a892881cc48e8b607))
* print full exception when patch fails ([7cf79e6](https://github.com/revanced/revanced-patcher/commit/7cf79e68e0e9dfd9faddee33139b127b71882d3e))

## [6.3.2-dev.1](https://github.com/revanced/revanced-patcher/compare/v6.3.1...v6.3.2-dev.1) (2022-12-18)


### Bug Fixes

* check if fingerprint string is substring of any string references ([c5de9e2](https://github.com/revanced/revanced-patcher/commit/c5de9e29889dffd18b31e62a892881cc48e8b607))
* print full exception when patch fails ([7cf79e6](https://github.com/revanced/revanced-patcher/commit/7cf79e68e0e9dfd9faddee33139b127b71882d3e))

## [6.3.2-dev.1](https://github.com/revanced/revanced-patcher/compare/v6.3.1...v6.3.2-dev.1) (2022-12-17)


### Bug Fixes

* print full exception when patch fails ([27a8401](https://github.com/revanced/revanced-patcher/commit/27a8401d81e078e0303f7ddcb0ac6f342f8e4def))

## [6.3.1](https://github.com/revanced/revanced-patcher/compare/v6.3.0...v6.3.1) (2022-12-13)


### Bug Fixes

* publicize types when merging files if necessary ([#137](https://github.com/revanced/revanced-patcher/issues/137)) ([9ec720e](https://github.com/revanced/revanced-patcher/commit/9ec720e983785d8b1dde330cc0e0e0f914c1803c))

## [6.3.1-dev.1](https://github.com/revanced/revanced-patcher/compare/v6.3.0...v6.3.1-dev.1) (2022-12-13)


### Bug Fixes

* publicize types when merging files if necessary ([#137](https://github.com/revanced/revanced-patcher/issues/137)) ([9ec720e](https://github.com/revanced/revanced-patcher/commit/9ec720e983785d8b1dde330cc0e0e0f914c1803c))

# [6.3.0](https://github.com/revanced/revanced-patcher/compare/v6.2.0...v6.3.0) (2022-12-02)


### Features

* sort patches in lexicographical order ([a306561](https://github.com/revanced/revanced-patcher/commit/a306561b55ac848792046378f582a036f7ffab03)), closes [#125](https://github.com/revanced/revanced-patcher/issues/125)

# [6.2.0](https://github.com/revanced/revanced-patcher/compare/v6.1.1...v6.2.0) (2022-12-02)


### Features

* merge classes on addition ([#127](https://github.com/revanced/revanced-patcher/issues/127)) ([a925650](https://github.com/revanced/revanced-patcher/commit/a9256500440f9b4117f1b8813ba0097dafee4ebb))

## [6.1.1](https://github.com/revanced/revanced-patcher/compare/v6.1.0...v6.1.1) (2022-11-25)


### Bug Fixes

* use `MethodUtil.methodSignaturesMatch` instead of `Method.softCompareTo` ([bd053b7](https://github.com/revanced/revanced-patcher/commit/bd053b7e9974c0282d56e6762459db7070452e4a))

# [6.1.0](https://github.com/revanced/revanced-patcher/compare/v6.0.2...v6.1.0) (2022-11-22)


### Features

* apply changes from ReVanced Patcher ([ba9d998](https://github.com/revanced/revanced-patcher/commit/ba9d99868103406fe36b9aa0cfaa0ed5023edfab))

## [6.0.2](https://github.com/revanced/revanced-patcher/compare/v6.0.1...v6.0.2) (2022-11-18)


### Bug Fixes

* fallback to patch class name instead of `java.lang.Class` class name ([4164cb0](https://github.com/revanced/revanced-patcher/commit/4164cb0deacc7e1eed9fce63dab030180f28b762))

## [6.0.1](https://github.com/revanced/revanced-patcher/compare/v6.0.0...v6.0.1) (2022-11-14)


### Bug Fixes

* remove unnecessary dummy nop instructions ([#111](https://github.com/revanced/revanced-patcher/issues/111)) ([f9bc95f](https://github.com/revanced/revanced-patcher/commit/f9bc95f220aa434308ce6950ba6ad2e7efac9c8a))

# [6.0.0](https://github.com/revanced/revanced-patcher/compare/v5.1.2...v6.0.0) (2022-10-05)


### Code Refactoring

* improve structuring of classes and their implementations ([4aa14bb](https://github.com/revanced/revanced-patcher/commit/4aa14bbb858af9253eae9328b759f3298b65a215))


### Features

* remove unused annotation `DirectPatternScanMethod` ([538b2a8](https://github.com/revanced/revanced-patcher/commit/538b2a859962570c700362afc88704ed3611aa87))
* remove unused annotation `SincePatcher` ([4ae9ad0](https://github.com/revanced/revanced-patcher/commit/4ae9ad09d64a3f69512ccb037f816cb847d7350f))
* remove unused extension `dependsOn` ([797286b](https://github.com/revanced/revanced-patcher/commit/797286b7588646272dea2fd35e8e78b0ffb18a0f))
* remove unused patch extensions ([5583904](https://github.com/revanced/revanced-patcher/commit/55839049948033ad02414517fd3ba03619216aec))


### BREAKING CHANGES

* various changes in which packages classes previously where and their implementation
* These extensions do not exist anymore and any use should be removed
* The extension does not exist anymore and any use should be removed
* The annotation does not exist anymore and any use should be removed

## [5.1.2](https://github.com/revanced/revanced-patcher/compare/v5.1.1...v5.1.2) (2022-09-29)


### Bug Fixes

* check dependencies for resource patches ([9c07ffc](https://github.com/revanced/revanced-patcher/commit/9c07ffcc7af9f088426528561f4321c5cc6b5b15))
* use instruction index instead of strings list index for `StringMatch` ([843e62a](https://github.com/revanced/revanced-patcher/commit/843e62ad290ee0a707be9322ee943921da3ea420))

## [5.1.1](https://github.com/revanced/revanced-patcher/compare/v5.1.0...v5.1.1) (2022-09-26)


### Performance Improvements

* decode resources only when necessary ([3ba4be2](https://github.com/revanced/revanced-patcher/commit/3ba4be240bf0a424e4bbfbaca9605644fda0984e))

# [5.1.0](https://github.com/revanced/revanced-patcher/compare/v5.0.1...v5.1.0) (2022-09-26)


### Features

* RwLock for opening files in `DomFileEditor` ([db4348c](https://github.com/revanced/revanced-patcher/commit/db4348c4faf51bfe29678baacfbe76ba645ec0b9))

## [5.0.1](https://github.com/revanced/revanced-patcher/compare/v5.0.0...v5.0.1) (2022-09-23)


### Reverts

* revert breaking changes ([#106](https://github.com/revanced/revanced-patcher/issues/106)) ([124332f](https://github.com/revanced/revanced-patcher/commit/124332f0e9bbdaf4f1aeeb6a31333093eeba1642))

# [5.0.0](https://github.com/revanced/revanced-patcher/compare/v4.5.0...v5.0.0) (2022-09-21)


### Bug Fixes

* **tests:** access `patternScanResult` through `scanResult` ([76676fb](https://github.com/revanced/revanced-patcher/commit/76676fb5673a9e92517ee3a13943cdc98dd5102a))


* refactor!: move utility methods from `MethodFingerprintUtils` `MethodFingerprint` ([d802ef8](https://github.com/revanced/revanced-patcher/commit/d802ef844edf65d4d26328d6ca72e3ddd5a52b15))
* feat(fingerprint)!: `StringsScanResult` for `MethodFingerprint` ([3813e28](https://github.com/revanced/revanced-patcher/commit/3813e28ac2ad6710d8d935526ca679e7b1b5980e))


### BREAKING CHANGES

* Imports will have to be updated from `MethodFingerprintUtils` to `MethodFingerprint.Companion`.

Signed-off-by: oSumAtrIX <johan.melkonyan1@web.de>
* `MethodFingerprint` now has a field for `MethodFingerprintScanResult`. `MethodFingerprintScanResult` now holds the previous field `MethodFingerprint.patternScanResult`.

Signed-off-by: oSumAtrIX <johan.melkonyan1@web.de>

# [4.5.0](https://github.com/revanced/revanced-patcher/compare/v4.4.2...v4.5.0) (2022-09-20)


### Features

* section `acknowledgements` for issue templates ([a0cb449](https://github.com/revanced/revanced-patcher/commit/a0cb449c60310917141e2809abaa16b4174dc002))

## [4.4.2](https://github.com/revanced/revanced-patcher/compare/v4.4.1...v4.4.2) (2022-09-18)


### Bug Fixes

* **fingerprint:** do not throw on `MethodFingerprint.result` getter ([2f7e62e](https://github.com/revanced/revanced-patcher/commit/2f7e62ef65422f2c75ef8b09b9cd27076e172b30))


### Performance Improvements

* **fingerprint:** do not resolve already resolved fingerprints ([4bfd7eb](https://github.com/revanced/revanced-patcher/commit/4bfd7ebff8b6623b0da4a46d6048bed08c5070d4))

## [4.4.1](https://github.com/revanced/revanced-patcher/compare/v4.4.0...v4.4.1) (2022-09-14)


### Bug Fixes

* compare any methods parameters ([#101](https://github.com/revanced/revanced-patcher/issues/101)) ([085a3a4](https://github.com/revanced/revanced-patcher/commit/085a3a479d7bd411dcb0492b283daca538c824a1))

# [4.4.0](https://github.com/revanced/revanced-patcher/compare/v4.3.0...v4.4.0) (2022-09-09)


### Features

* add PathOption back ([172655b](https://github.com/revanced/revanced-patcher/commit/172655bde06efdb0955431b44d269e6a64fe317a))

# [4.3.0](https://github.com/revanced/revanced-patcher/compare/v4.2.3...v4.3.0) (2022-09-09)


### Features

* improved Patch Options ([e722e3f](https://github.com/revanced/revanced-patcher/commit/e722e3f4f9dc64acf53595802a0a83cf46ee96b8))

## [4.2.3](https://github.com/revanced/revanced-patcher/compare/v4.2.2...v4.2.3) (2022-09-08)


### Bug Fixes

* wrong value for iterator in PatchOptions ([e31ac1f](https://github.com/revanced/revanced-patcher/commit/e31ac1f132df56ba7d2f8446d289ae03ef28f67d))

## [4.2.2](https://github.com/revanced/revanced-patcher/compare/v4.2.1...v4.2.2) (2022-09-08)


### Bug Fixes

* invalid type propagation in options ([b873228](https://github.com/revanced/revanced-patcher/commit/b873228ef0a9e6e431a4278c979caa5fcc508e0d)), closes [#98](https://github.com/revanced/revanced-patcher/issues/98)

## [4.2.1](https://github.com/revanced/revanced-patcher/compare/v4.2.0...v4.2.1) (2022-09-08)


### Bug Fixes

* make patcher version public ([76c45dd](https://github.com/revanced/revanced-patcher/commit/76c45dd7c1ffdca57e30ae7109c9fe0e5768f877))

# [4.2.0](https://github.com/revanced/revanced-patcher/compare/v4.1.5...v4.2.0) (2022-09-08)


### Bug Fixes

* remove repeatable from PatchDeprecated ([6e73631](https://github.com/revanced/revanced-patcher/commit/6e73631d4d21e5e862f07ed7517244f36394e5ca))


### Features

* SincePatcher annotation ([25f74dc](https://github.com/revanced/revanced-patcher/commit/25f74dc5e9ed1a09258345b920d4f5a0dd7da527))

## [4.1.5](https://github.com/revanced/revanced-patcher/compare/v4.1.4...v4.1.5) (2022-09-08)


### Bug Fixes

* broken deprecation message ([62aa295](https://github.com/revanced/revanced-patcher/commit/62aa295e7372014238415af36d902a4e88e2acbc))

## [4.1.4](https://github.com/revanced/revanced-patcher/compare/v4.1.3...v4.1.4) (2022-09-08)


### Bug Fixes

* handle option types and nulls properly ([aff4968](https://github.com/revanced/revanced-patcher/commit/aff4968e6f67239afa3b5c02cc133a17d9c3cbeb))

## [4.1.3](https://github.com/revanced/revanced-patcher/compare/v4.1.2...v4.1.3) (2022-09-07)


### Bug Fixes

* only run list option check if not null ([4055939](https://github.com/revanced/revanced-patcher/commit/4055939c089e3c396c308c980215d93a1dea5954))

## [4.1.2](https://github.com/revanced/revanced-patcher/compare/v4.1.1...v4.1.2) (2022-09-07)


### Bug Fixes

* invalid types for example options ([79f91e0](https://github.com/revanced/revanced-patcher/commit/79f91e0e5a6d99828f30aae55339ce0d897394c7))

## [4.1.1](https://github.com/revanced/revanced-patcher/compare/v4.1.0...v4.1.1) (2022-09-07)


### Bug Fixes

* handle private companion objects ([ad3d332](https://github.com/revanced/revanced-patcher/commit/ad3d332e27d07e9d074bbaaf51af7eb2f9bfc7d5))

# [4.1.0](https://github.com/revanced/revanced-patcher/compare/v4.0.0...v4.1.0) (2022-09-07)


### Features

* deprecation for patches ([80c2e80](https://github.com/revanced/revanced-patcher/commit/80c2e809251cdb04d2dd3b3bfdbb8844bdfa31fa))

# [4.0.0](https://github.com/revanced/revanced-patcher/compare/v3.5.1...v4.0.0) (2022-09-07)


### Code Refactoring

* Improve Patch Options ([6b909c1](https://github.com/revanced/revanced-patcher/commit/6b909c1ee6b8c2ea08bbca059df755e2e5f31656))


### BREAKING CHANGES

* Options has been moved from Patch to a new interface called OptionsContainer and are now handled entirely different. Make sure to check the examples to understand how it works.

## [3.5.1](https://github.com/revanced/revanced-patcher/compare/v3.5.0...v3.5.1) (2022-09-06)


### Bug Fixes

* add tests for PathOption ([d6308e1](https://github.com/revanced/revanced-patcher/commit/d6308e126c6217b098192c51b6e98bc85a8656bd))
* PathOption should be open, not sealed ([a562e47](https://github.com/revanced/revanced-patcher/commit/a562e476c085841efbc7ee98b01d8e6bb18ed757))
* typo in ListOption ([3921648](https://github.com/revanced/revanced-patcher/commit/392164862c83d6e76b2a2113d6f6d59fef0020d1))


### Performance Improvements

* make exception an object ([75d2be8](https://github.com/revanced/revanced-patcher/commit/75d2be88037c9cf5436ab69d92abea575409a865))

# [3.5.0](https://github.com/revanced/revanced-patcher/compare/v3.4.1...v3.5.0) (2022-09-05)


### Features

* default value for `Package.versions` annotation parameter ([131dedd](https://github.com/revanced/revanced-patcher/commit/131dedd4b021fe1c3b0be49ccba4764b325770ea))

## [3.4.1](https://github.com/revanced/revanced-patcher/compare/v3.4.0...v3.4.1) (2022-09-03)


### Bug Fixes

* remove default param from Package.versions ([4b81318](https://github.com/revanced/revanced-patcher/commit/4b813187107e85dc267dbc2d353884b2cc671cc4))

# [3.4.0](https://github.com/revanced/revanced-patcher/compare/v3.3.3...v3.4.0) (2022-08-31)


### Features

* nullable parameters ([7882a8d](https://github.com/revanced/revanced-patcher/commit/7882a8d928cad8de8cfea711947fc02659549d20))

## [3.3.3](https://github.com/revanced/revanced-patcher/compare/v3.3.2...v3.3.3) (2022-08-14)


### Bug Fixes

* show error message if cause is null ([f9da2ad](https://github.com/revanced/revanced-patcher/commit/f9da2ad531644617ad5a2cc6a1819d530e18ba22))

## [3.3.2](https://github.com/revanced/revanced-patcher/compare/v3.3.1...v3.3.2) (2022-08-06)


### Bug Fixes

* close open files ([#75](https://github.com/revanced/revanced-patcher/issues/75)) ([123ad54](https://github.com/revanced/revanced-patcher/commit/123ad54c150bd04f4b8ef5c65334ea468ceb99cc))

## [3.3.1](https://github.com/revanced/revanced-patcher/compare/v3.3.0...v3.3.1) (2022-08-03)


### Bug Fixes

* revert soft dependencies ([7b2d058](https://github.com/revanced/revanced-patcher/commit/7b2d058144b0718992d329731e2af7cc704e4370))

# [3.3.0](https://github.com/revanced/revanced-patcher/compare/v3.2.1...v3.3.0) (2022-08-02)


### Features

* add getValue & setValue for PatchOption ([2572cd0](https://github.com/revanced/revanced-patcher/commit/2572cd04b5da4eeae738c8dde31493177edf0bf8))

## [3.2.1](https://github.com/revanced/revanced-patcher/compare/v3.2.0...v3.2.1) (2022-08-02)


### Bug Fixes

* check if patch option requirement is met ([14a73bf](https://github.com/revanced/revanced-patcher/commit/14a73bfcafac36bce2b8466788d460edde7a14fd))

# [3.2.0](https://github.com/revanced/revanced-patcher/compare/v3.1.0...v3.2.0) (2022-08-02)


### Features

* PatchOptions#nullify to nullify an option ([371f0c4](https://github.com/revanced/revanced-patcher/commit/371f0c4d0bf96e7f6db35085efccaed3000a096c))

# [3.1.0](https://github.com/revanced/revanced-patcher/compare/v3.0.0...v3.1.0) (2022-08-02)


### Features

* validator for patch options ([4e2e772](https://github.com/revanced/revanced-patcher/commit/4e2e77238957d7732326cfe5e05145bf7dab5bfb))

# [3.0.0](https://github.com/revanced/revanced-patcher/compare/v2.9.0...v3.0.0) (2022-08-02)


### Features

* registry for patch options ([2431785](https://github.com/revanced/revanced-patcher/commit/2431785d0e494d6271c6951eec9adfff9db95c17))


### BREAKING CHANGES

* Patch options now use the PatchOptions registry class instead of an Iterable. This change requires modifications to existing patches using this API.

# [2.9.0](https://github.com/revanced/revanced-patcher/compare/v2.8.0...v2.9.0) (2022-08-02)


### Bug Fixes

* show error message instead of `null` ([8d95b14](https://github.com/revanced/revanced-patcher/commit/8d95b14f350b47ec029f35e776f6e627aaf5f607))


### Features

* exclusive mutable access to files ([814ce0b](https://github.com/revanced/revanced-patcher/commit/814ce0b9ae29725417c86b7d11b40d025724a426))

# [2.8.0](https://github.com/revanced/revanced-patcher/compare/v2.7.0...v2.8.0) (2022-08-01)


### Bug Fixes

* remove requirement for solution [skip ci] ([#80](https://github.com/revanced/revanced-patcher/issues/80)) ([9a4d30e](https://github.com/revanced/revanced-patcher/commit/9a4d30e15234ef62844f035c58a1143674d4c12e))


### Features

* patch options ([#81](https://github.com/revanced/revanced-patcher/issues/81)) ([fbb09f3](https://github.com/revanced/revanced-patcher/commit/fbb09f38dce49adc7f63b71bdf2df2ef0b84db04))

# [2.7.0](https://github.com/revanced/revanced-patcher/compare/v2.6.0...v2.7.0) (2022-08-01)


### Features

* `Closeable` patches ([bbd40bf](https://github.com/revanced/revanced-patcher/commit/bbd40bf2f6ff200705f2bcb272dd1680bb244e3f))

# [2.6.0](https://github.com/revanced/revanced-patcher/compare/v2.5.2...v2.6.0) (2022-07-31)


### Features

* add Patch#dependsOn extension ([523f67b](https://github.com/revanced/revanced-patcher/commit/523f67b238646caaa9b7676a0e238ce82adbdda4))
* Soft Dependencies for Patches ([8c12f8d](https://github.com/revanced/revanced-patcher/commit/8c12f8d488f939cc932e826aad0b20876ae165b7))

## [2.5.2](https://github.com/revanced/revanced-patcher/compare/v2.5.1...v2.5.2) (2022-07-24)

## [2.5.1](https://github.com/revanced/revanced-patcher/compare/v2.5.0...v2.5.1) (2022-07-17)


### Bug Fixes

* close stream when closing `DomFileEditor` ([77604d4](https://github.com/revanced/revanced-patcher/commit/77604d40785847b775155c0e75b663a3c7336aa3))

# [2.5.0](https://github.com/revanced/revanced-patcher/compare/v2.4.0...v2.5.0) (2022-07-11)


### Bug Fixes

* missing additional items [skip ci] ([0ebab8b](https://github.com/revanced/revanced-patcher/commit/0ebab8bf598d993df6e340651205cba48f1ef725))


### Features

* feature request issue template ([1b39278](https://github.com/revanced/revanced-patcher/commit/1b39278b24ba2f964d93bd8ad2e28472ee036d90))
* issue templates [skip ci] ([112bc99](https://github.com/revanced/revanced-patcher/commit/112bc998f4761a647cb9eab7454e35264fa96fd9))

# [2.4.0](https://github.com/revanced/revanced-patcher/compare/v2.3.1...v2.4.0) (2022-07-09)


### Features

* Improve Smali Compiler ([6bfe571](https://github.com/revanced/revanced-patcher/commit/6bfe5716c38181bbe9476b5c6ad29526edb4e022))

## [2.3.1](https://github.com/revanced/revanced-patcher/compare/v2.3.0...v2.3.1) (2022-07-07)


### Bug Fixes

* handle null properly ([#64](https://github.com/revanced/revanced-patcher/issues/64)) ([482af78](https://github.com/revanced/revanced-patcher/commit/482af78f2ba23b8003fc9961df5fde54d7295d5c))

# [2.3.0](https://github.com/revanced/revanced-patcher/compare/v2.2.2...v2.3.0) (2022-07-05)


### Features

* nullability for `BytecodePatch` constructor ([#59](https://github.com/revanced/revanced-patcher/issues/59)) ([4ea030d](https://github.com/revanced/revanced-patcher/commit/4ea030d0a03f736bbecbd491317ba2167b18fe94))

## [2.2.2](https://github.com/revanced/revanced-patcher/compare/v2.2.1...v2.2.2) (2022-07-04)


### Bug Fixes

* `MethodWalker` not accounting for all reference instructions ([48068cb](https://github.com/revanced/revanced-patcher/commit/48068cb3d79e283ff1cad9f3f78dc1d0fcd14f83))

## [2.2.1](https://github.com/revanced/revanced-patcher/compare/v2.2.0...v2.2.1) (2022-07-03)


### Bug Fixes

* more useful error message ([4b2e323](https://github.com/revanced/revanced-patcher/commit/4b2e3230ec74fa3a57ae86067e5cb7cecbe45013))

# [2.2.0](https://github.com/revanced/revanced-patcher/compare/v2.1.2...v2.2.0) (2022-07-02)


### Bug Fixes

* DomFileEditor opening in- and output streams on the same file ([83187c9](https://github.com/revanced/revanced-patcher/commit/83187c9edd7b088bc18960c5eb9a2042ca536b5f))


### Features

* remove deprecated functions ([ada5a03](https://github.com/revanced/revanced-patcher/commit/ada5a033de3cf94e7255ec2d522520f86431f001))
* streams overload for `XmlFileHolder` ([6f72c4c](https://github.com/revanced/revanced-patcher/commit/6f72c4c4c051e48c8d03d2a7b2cfc1c53028ed86))

# [2.2.0-dev.3](https://github.com/revanced/revanced-patcher/compare/v2.2.0-dev.2...v2.2.0-dev.3) (2022-07-02)


### Bug Fixes

* DomFileEditor opening in- and output streams on the same file ([83187c9](https://github.com/revanced/revanced-patcher/commit/83187c9edd7b088bc18960c5eb9a2042ca536b5f))

# [2.2.0-dev.2](https://github.com/revanced/revanced-patcher/compare/v2.2.0-dev.1...v2.2.0-dev.2) (2022-07-02)


### Features

* streams overload for `XmlFileHolder` ([6f72c4c](https://github.com/revanced/revanced-patcher/commit/6f72c4c4c051e48c8d03d2a7b2cfc1c53028ed86))

# [2.2.0-dev.1](https://github.com/revanced/revanced-patcher/compare/v2.1.2...v2.2.0-dev.1) (2022-07-02)


### Features

* remove deprecated functions ([ada5a03](https://github.com/revanced/revanced-patcher/commit/ada5a033de3cf94e7255ec2d522520f86431f001))

## [2.1.2](https://github.com/revanced/revanced-patcher/compare/v2.1.1...v2.1.2) (2022-06-29)


### Bug Fixes

* invert fingerprint resolution condition of `customFingerprint` ([e2faf4c](https://github.com/revanced/revanced-patcher/commit/e2faf4ca9b6de23300b20ab471ee9dc365b04339))

## [2.1.1](https://github.com/revanced/revanced-patcher/compare/v2.1.0...v2.1.1) (2022-06-28)

# [2.1.0](https://github.com/revanced/revanced-patcher/compare/v2.0.4...v2.1.0) (2022-06-28)


### Features

* log failed patches due to failed dependencies ([a467fbb](https://github.com/revanced/revanced-patcher/commit/a467fbb704eebe812cdec14025398dab2af43959))

## [2.0.4](https://github.com/revanced/revanced-patcher/compare/v2.0.3...v2.0.4) (2022-06-27)

## [2.0.3](https://github.com/revanced/revanced-patcher/compare/v2.0.2...v2.0.3) (2022-06-27)

## [2.0.2](https://github.com/revanced/revanced-patcher/compare/v2.0.1...v2.0.2) (2022-06-27)

## [2.0.1](https://github.com/revanced/revanced-patcher/compare/v2.0.0...v2.0.1) (2022-06-26)


### Bug Fixes

* use `Exception` instead of `MethodNotFoundException` ([2fc4ec4](https://github.com/revanced/revanced-patcher/commit/2fc4ec40217a917ea6106ddc87be332f725aa13c))

# [2.0.0](https://github.com/revanced/revanced-patcher/compare/v1.11.0...v2.0.0) (2022-06-26)


### Code Refactoring

* migrate from `Signature` to `Fingerprint` ([efa8ea1](https://github.com/revanced/revanced-patcher/commit/efa8ea144528fcff588e782468845c315a7d6abd))


### BREAKING CHANGES

* Not backwards compatible, since a lot of classes where renamed.

# [1.11.0](https://github.com/revanced/revanced-patcher/compare/v1.10.2...v1.11.0) (2022-06-24)


### Features

* add replace and remove extensions ([#50](https://github.com/revanced/revanced-patcher/issues/50)) ([92ac5e4](https://github.com/revanced/revanced-patcher/commit/92ac5e4dc25f612856e2b5e528cf5fd48a5f20af))

## [1.10.2](https://github.com/revanced/revanced-patcher/compare/v1.10.1...v1.10.2) (2022-06-23)


### Bug Fixes

* dexlib must be propagated ([b738dcd](https://github.com/revanced/revanced-patcher/commit/b738dcd7ea04f5fe56e66af46fb11541fe54f6af))

## [1.10.1](https://github.com/revanced/revanced-patcher/compare/v1.10.0...v1.10.1) (2022-06-23)


### Bug Fixes

* callback only when inteded ([e3bf367](https://github.com/revanced/revanced-patcher/commit/e3bf367ad6615b30b06027d65f906b2588567a7f))
* mutability of local variable `modified` ([0e87ef5](https://github.com/revanced/revanced-patcher/commit/0e87ef56c418d5c37d58abb9b27f85e25fd44f81))

# [1.10.0](https://github.com/revanced/revanced-patcher/compare/v1.9.0...v1.10.0) (2022-06-23)


### Features

* improve logging ([c20dfe1](https://github.com/revanced/revanced-patcher/commit/c20dfe12d5c737264b844e6634de11bf1e1629f0))

# [1.9.0](https://github.com/revanced/revanced-patcher/compare/v1.8.0...v1.9.0) (2022-06-22)


### Bug Fixes

* callback for each file instead of class ([930768d](https://github.com/revanced/revanced-patcher/commit/930768dfb31dc5fa6c248050b08ac117c40ee0a3))


### Features

* yield the patch result ([dde5385](https://github.com/revanced/revanced-patcher/commit/dde5385232abddc8a85d6e9a939549b71dd9130e))

# [1.8.0](https://github.com/revanced/revanced-patcher/compare/v1.7.2...v1.8.0) (2022-06-22)


### Features

* logging class ([caf2745](https://github.com/revanced/revanced-patcher/commit/caf2745805ffd4b59fa81e79cc489b1a1a5c5d89))

## [1.7.2](https://github.com/revanced/revanced-patcher/compare/v1.7.1...v1.7.2) (2022-06-22)


### Bug Fixes

* add execute permission to `./gradlew` file ([#46](https://github.com/revanced/revanced-patcher/issues/46)) ([34f607a](https://github.com/revanced/revanced-patcher/commit/34f607aa24d89a777d906cc887203f343ce3fd07))

## [1.7.1](https://github.com/revanced/revanced-patcher/compare/v1.7.0...v1.7.1) (2022-06-22)


### Reverts

* revert "feat: use of `java.util.logging.Logger`" ([e8488b3](https://github.com/revanced/revanced-patcher/commit/e8488b3e86e0132011824f8ecba29e64f8db0573))

# [1.7.0](https://github.com/revanced/revanced-patcher/compare/v1.6.0...v1.7.0) (2022-06-22)


### Features

* migrate logger to `slf4j` ([8f66f9f](https://github.com/revanced/revanced-patcher/commit/8f66f9f606a785ac947b0e553822877f211d82df))

# [1.6.0](https://github.com/revanced/revanced-patcher/compare/v1.5.0...v1.6.0) (2022-06-22)


### Features

* use of `java.util.logging.Logger` ([9c39c9e](https://github.com/revanced/revanced-patcher/commit/9c39c9efdb5d48ddaffce7f711c275e732b0b2d9))

# [1.5.0](https://github.com/revanced/revanced-patcher/compare/v1.4.0...v1.5.0) (2022-06-22)


### Features

* use streams to write the dex files ([64bae88](https://github.com/revanced/revanced-patcher/commit/64bae884dcb72550a3218e149f3ca0fd0ca03aaf))

# [1.4.0](https://github.com/revanced/revanced-patcher/compare/v1.3.4...v1.4.0) (2022-06-22)


### Features

* return a `File` instance instead of `ExtFile` ([68174bb](https://github.com/revanced/revanced-patcher/commit/68174bbd6b4df47a91b610c2b97dbae55b594163))

## [1.3.4](https://github.com/revanced/revanced-patcher/compare/v1.3.3...v1.3.4) (2022-06-21)


### Bug Fixes

* `String.toInstructions` defaulting `forStaticMethod` to `false` ([5a2f02b](https://github.com/revanced/revanced-patcher/commit/5a2f02b97dcde95dbe901fa68cca6c6c0219cb82)), closes [revanced/revanced-patches#46](https://github.com/revanced/revanced-patches/issues/46)

## [1.3.3](https://github.com/revanced/revanced-patcher/compare/v1.3.2...v1.3.3) (2022-06-21)


### Bug Fixes

* add docs (trigger release) ([6628b78](https://github.com/revanced/revanced-patcher/commit/6628b7870fc052da40be0d50a7e2b0b6c57743cc))


### Reverts

* propagate dependencies ([365e1d7](https://github.com/revanced/revanced-patcher/commit/365e1d7a4507b918a4c8170ce2c88f6c8ff1d474))

## [1.3.2](https://github.com/revanced/revanced-patcher/compare/v1.3.1...v1.3.2) (2022-06-21)


### Bug Fixes

* return resourceFile to caller ([1f75777](https://github.com/revanced/revanced-patcher/commit/1f75777cf985bf08483033ec541937d3e733347b))

## [1.3.1](https://github.com/revanced/revanced-patcher/compare/v1.3.0...v1.3.1) (2022-06-21)


### Bug Fixes

* `InlineSmaliCompiler.compile` using 0 registers instead of 1 by default ([835a421](https://github.com/revanced/revanced-patcher/commit/835a421cc0588b92c2995e9d74727069d14b1750))

# [1.3.0](https://github.com/revanced/revanced-patcher/compare/v1.2.9...v1.3.0) (2022-06-20)


### Features

* `parametersCount` for `InlineSmaliCompiler` instead of `parameters` ([ad6c5c8](https://github.com/revanced/revanced-patcher/commit/ad6c5c827389d10eae473dc66557a699df8c3280))
* simplify adding instructions ([e47b67d](https://github.com/revanced/revanced-patcher/commit/e47b67d7ec521f288644afb89baf4146dc9bc87d))

## [1.2.9](https://github.com/revanced/revanced-patcher/compare/v1.2.8...v1.2.9) (2022-06-20)


### Bug Fixes

* update apktool ([ab866bb](https://github.com/revanced/revanced-patcher/commit/ab866bb8ef4792d8f2a51edc79e687b5b636c621))

## [1.2.8](https://github.com/revanced/revanced-patcher/compare/v1.2.7...v1.2.8) (2022-06-18)


### Bug Fixes

* update apktool ([051afd9](https://github.com/revanced/revanced-patcher/commit/051afd98d065f71556392139d77c20b4c2dc7dd1))

## [1.2.7](https://github.com/revanced/revanced-patcher/compare/v1.2.6...v1.2.7) (2022-06-18)


### Bug Fixes

* version not working with apktool due to cache ([03f5ee0](https://github.com/revanced/revanced-patcher/commit/03f5ee088b1b96b88cb7aeb323443b6209a13950))

## [1.2.6](https://github.com/revanced/revanced-patcher/compare/v1.2.5...v1.2.6) (2022-06-18)


### Bug Fixes

* remove javadoc jar (also trigger release) ([56f6ca3](https://github.com/revanced/revanced-patcher/commit/56f6ca38919b522c0d5558eabffa4aee41cc0b0b))

## [1.2.5](https://github.com/revanced/revanced-patcher/compare/v1.2.4...v1.2.5) (2022-06-17)


### Bug Fixes

* goodbye security ([8f3ac77](https://github.com/revanced/revanced-patcher/commit/8f3ac7702a2b3ee98c55aeac6a1b9972f99664cc))

## [1.2.4](https://github.com/revanced/revanced-patcher/compare/v1.2.3...v1.2.4) (2022-06-15)


### Reverts

* "fix: enforce aapt v1" ([dfd8a24](https://github.com/revanced/revanced-patcher/commit/dfd8a245124f85b1b028bbba197c70c8dca689b6))

## [1.2.3](https://github.com/revanced/revanced-patcher/compare/v1.2.2...v1.2.3) (2022-06-14)


### Bug Fixes

* enforce aapt v1 ([cff87ff](https://github.com/revanced/revanced-patcher/commit/cff87ff0770d774d7ef79eec5a22462eadbcb9c5))

## [1.2.2](https://github.com/revanced/revanced-patcher/compare/v1.2.1...v1.2.2) (2022-06-14)


### Bug Fixes

* enforce aapt v2 ([b68b0bf](https://github.com/revanced/revanced-patcher/commit/b68b0bf3d735f54b92ad7dad8132f77e9007063f))

## [1.2.1](https://github.com/revanced/revanced-patcher/compare/v1.2.0...v1.2.1) (2022-06-14)


### Bug Fixes

* Patcher setting BuildOptions too late ([6a5c873](https://github.com/revanced/revanced-patcher/commit/6a5c8735fb8a5d6f7e9c606734b6684c7fa99e7f))

# [1.2.0](https://github.com/revanced/revanced-patcher/compare/v1.1.0...v1.2.0) (2022-06-14)


### Features

* allow custom framework path to be specified ([d3a580e](https://github.com/revanced/revanced-patcher/commit/d3a580ea19d7c2d5d8c97650b1e6396ea0a7fc25))

# [1.1.0](https://github.com/revanced/revanced-patcher/compare/v1.0.0...v1.1.0) (2022-06-11)


### Bug Fixes

* resource patcher ([31815ca](https://github.com/revanced/revanced-patcher/commit/31815ca9ea990f16b3600d61fd570c1805be1c82))
* update apktool to fork ([566ecef](https://github.com/revanced/revanced-patcher/commit/566ecefa2bd4cde5ebfb2b22dc56cd8bf9f396bd))


### Features

* allow custom aapt path to be specified ([8eb4a8f](https://github.com/revanced/revanced-patcher/commit/8eb4a8f87ae7679a272f3224273a37a31d4bb121))

# 1.0.0 (2022-06-05)


### Bug Fixes

* `compareSignatureToMethod` not matching correctly in case opcodes are null ([cca12aa](https://github.com/revanced/revanced-patcher/commit/cca12aa34a60d766c02e55241df847f7d230d4d7))
* `ConcurrentModificationException` while iterating through `proxies` and modifying it ([6cb7cdb](https://github.com/revanced/revanced-patcher/commit/6cb7cdb0b2a2b954adb04033e0f2d3ccb4604545))
* `JarPatchBundle` loading non-class files to class loader ([849616d](https://github.com/revanced/revanced-patcher/commit/849616dc2b6e30ec1fa1d8a8f9c1f881fc11676a))
* `PackageMetadata` ([7399450](https://github.com/revanced/revanced-patcher/commit/739945013962fd80d2635fff126d84046870f956))
* `replaceWith` not replacing classes with used class proxies ([4178a1e](https://github.com/revanced/revanced-patcher/commit/4178a1eedce1436ffeb3ddd6952ce0b6ec87d5a0))
* adding existing classes to the patchers cache ([9659a61](https://github.com/revanced/revanced-patcher/commit/9659a61c5c3a84714160b78b32cc337a97c8caa9))
* always return PatchResultSuccess on patch success ([996c4ac](https://github.com/revanced/revanced-patcher/commit/996c4acb2061db776430ad8b07bfdb3fe32861f6))
* applying no patches throwing error ([5ca5a1c](https://github.com/revanced/revanced-patcher/commit/5ca5a1c29e087ce7e4b6d5e593b775365803151d))
* applyPatches not returning successful patches ([f806cb3](https://github.com/revanced/revanced-patcher/commit/f806cb38c571cdd22016396ee1874ee18c91b79f))
* avoid ignoring test resources (fixes [#1](https://github.com/revanced/revanced-patcher/issues/1)) ([d5a3c76](https://github.com/revanced/revanced-patcher/commit/d5a3c76389ba902c22ddc8b7ba1a110b7ff852df))
* Classes not being written properly because of array shifting ([6e4db11](https://github.com/revanced/revanced-patcher/commit/6e4db110c8fdd16fb0c0ce81f427d84f2a3b6ee0))
* current must be calculated after increment ([5f12bab](https://github.com/revanced/revanced-patcher/commit/5f12bab5df97fbe6e2e62c1bf2814a2e682ab4f3))
* failing tests temporarily ([fc05fe7](https://github.com/revanced/revanced-patcher/commit/fc05fe79deec2486bb746d33e803ad052e68f8de))
* fix classes having multiple instances of fields ([7cc8a7d](https://github.com/revanced/revanced-patcher/commit/7cc8a7dec321774c1d3f2f1a87ac91f952c4fb7e))
* fix classes having multiple method instances ([398239d](https://github.com/revanced/revanced-patcher/commit/398239dc10a3ea04e46adb3be176c897876e5587))
* Fixed writer & signature resolver, improved tests & speed, minor refactoring ([e6c2501](https://github.com/revanced/revanced-patcher/commit/e6c2501539540301d5b70014de460e5452a09b04))
* fuzzy resolver warning params were turned around ([e5bea06](https://github.com/revanced/revanced-patcher/commit/e5bea06353805f004d607124a8ebed138f84d583))
* give ClassWriter a ClassReader for symtable ([41749ba](https://github.com/revanced/revanced-patcher/commit/41749ba8290b2dec5dd2ab6e0bc9d714887a1a05))
* **gradle:** publish source and javadocs ([c236ebe](https://github.com/revanced/revanced-patcher/commit/c236ebe0789f9c78d610769f0feda2b64fa4a128))
* incorrect pattern offset ([f3b5f67](https://github.com/revanced/revanced-patcher/commit/f3b5f67b395167c1b9411b2374f3ef584b57b6cf))
* **Io:** fix finding classes by name ([b957501](https://github.com/revanced/revanced-patcher/commit/b957501e709028005c4d6c7857022980205b6861))
* **Io:** JAR loading and saving ([#8](https://github.com/revanced/revanced-patcher/issues/8)) ([310a7c4](https://github.com/revanced/revanced-patcher/commit/310a7c446b547d84b02c5da2161958e77ce69f0d))
* make `methodMetadata` nullable in `MethodSignatureMetadata` ([4e56652](https://github.com/revanced/revanced-patcher/commit/4e566524299674426fb0344d09db3b0c1cb3d300))
* make warnings nullable instead of lateinit ([8f1a629](https://github.com/revanced/revanced-patcher/commit/8f1a629191668e05917dc797e486647e55276d59))
* match to correct signature method parameters ([1ee2e4b](https://github.com/revanced/revanced-patcher/commit/1ee2e4ba56097c5e06c93c9ce04cb5543f0e4a67))
* **MethodResolver:** fix cd57a8c9a0db7e3ae5ad0bca202e5955930319ab ([cbd8df2](https://github.com/revanced/revanced-patcher/commit/cbd8df2df008ef37c6b43e2a8442c41f24be9358))
* **MethodResolver:** strip labels and line numbers so opcode patterns match ([699c730](https://github.com/revanced/revanced-patcher/commit/699c730a7cecf31878827d645e845490a37de4cb))
* **MethodResolver:** strip labels nodes so opcode patterns match ([82c5306](https://github.com/revanced/revanced-patcher/commit/82c530650f926dd026d263cfe23a7d67cb27bbf2))
* MethodSignature#resolved throwing an exception ([c612676](https://github.com/revanced/revanced-patcher/commit/c612676543282155143471b71a095e26023806ea))
* Move proxy package out of cache package ([ce21bd6](https://github.com/revanced/revanced-patcher/commit/ce21bd60f34d78b94d6d85f2c5375bc934ed4091))
* null check causing an exception ([338bd9f](https://github.com/revanced/revanced-patcher/commit/338bd9f7394afd84e5e195a7f8155c813812cfb5))
* nullable signature members ([#10](https://github.com/revanced/revanced-patcher/issues/10)) ([674461f](https://github.com/revanced/revanced-patcher/commit/674461f08daabbf92cb54e4eadb408226fac47af))
* Patch should have access to the Cache ([4dd820f](https://github.com/revanced/revanced-patcher/commit/4dd820ffdf1b98fe41b50f7cb2670b89acfbb99d))
* Patcher not writing resolved methods ([fac44a5](https://github.com/revanced/revanced-patcher/commit/fac44a50c39d8c102bd3e7ca4dd1bb86d29f7b57))
* qualifying `Element` with wrong package ([024fa86](https://github.com/revanced/revanced-patcher/commit/024fa867e115f984cfa3e395b78f4f43aa81709b))
* reaching all constructors not possible ([c459beb](https://github.com/revanced/revanced-patcher/commit/c459beb5f898d797f2f03ed36326bd9cfad03d31))
* reformat (trigger release) ([bf48945](https://github.com/revanced/revanced-patcher/commit/bf4894592bf9ee9c6233abc91f538b7b8ef986a0))
* remove broken code ([0e72a6e](https://github.com/revanced/revanced-patcher/commit/0e72a6e85ff9a6035510680fc5e33ab0cd14144f))
* remove dependency to fork of Apktool ([11abc67](https://github.com/revanced/revanced-patcher/commit/11abc67d9ab7d7b273fd4cd4c53af54008a80585))
* remove leftover debug code ([0f30eac](https://github.com/revanced/revanced-patcher/commit/0f30eac32ce66d8b90906c02ef7e7854feeecc33))
* return mutable set of classes ([66a9b76](https://github.com/revanced/revanced-patcher/commit/66a9b768457e98fdde0b61f9a8d6aed4c1872027))
* returning failure on success ([48c4ea2](https://github.com/revanced/revanced-patcher/commit/48c4ea2f6d9de319383a49ea2d4c6ffb4f687a2b))
* Search method map for existing class proxy ([a1e909b](https://github.com/revanced/revanced-patcher/commit/a1e909b16337c538f8f8b475801d8b1804163bfe))
* set index for insertAt to 0 by default ([d5b4c99](https://github.com/revanced/revanced-patcher/commit/d5b4c99c00272e3e5afec2fa0a489ba618f2a81a))
* set marklimit to Integer.MAX_VALUE ([e6e468f](https://github.com/revanced/revanced-patcher/commit/e6e468fbb5c20b08c8bd59bafc794acea907e4b4))
* string signature in `SignatureResolver` ([e5ae970](https://github.com/revanced/revanced-patcher/commit/e5ae9700096924e63b15a08079dce40ae07202d8))
* Suppress unused for addFiles ([3d6a1d3](https://github.com/revanced/revanced-patcher/commit/3d6a1d38f339ce2c5d82b7ac46c208c6702d6d44))
* throwing in case the opcode patterns do not match ([3144ec8](https://github.com/revanced/revanced-patcher/commit/3144ec872ac8651b8c0a9311ae508d5c3cc734ce))
* use Array instead of Iterable for methodParameters ([dfac8f0](https://github.com/revanced/revanced-patcher/commit/dfac8f03a362fd273527f552d9eae121505fd4e0))
* using old instance of `Androlib` when saving ([a4d8be2](https://github.com/revanced/revanced-patcher/commit/a4d8be20fcd444b08ec9c43f9f7029f8bacbbc41))
* workflow on dev branch ([428f7f4](https://github.com/revanced/revanced-patcher/commit/428f7f4decb00d28c9bf137ef4cd1d5fd4a0821e))
* write all classes ([f068fc8](https://github.com/revanced/revanced-patcher/commit/f068fc87ff8e204826639318af39e48e683254da))


### Code Refactoring

* bump multidexlib2 to 2.5.2.r2 ([a6c6b49](https://github.com/revanced/revanced-patcher/commit/a6c6b4979af42936cb26608541a4f7a66393b3f0))
* Change all references from Array to Iterable ([72f3cad](https://github.com/revanced/revanced-patcher/commit/72f3cad3f98001b0109b07373ed9cc57a9001cfa))
* convert Patch to abstract class ([cb9b1b9](https://github.com/revanced/revanced-patcher/commit/cb9b1b9416c699c68d0fca228d4f8ca6fb634cb5))
* Optimize Signature class ([#11](https://github.com/revanced/revanced-patcher/issues/11)) ([7faa001](https://github.com/revanced/revanced-patcher/commit/7faa001406c1f28dc2182cf6d1ab19504f4e3eb9))
* Rename `net.revanced` to `app.revanced` ([7087230](https://github.com/revanced/revanced-patcher/commit/70872307e33282b37dd5fb315b56022ab73bf582))


### Features

* `Dependencies` annotation ([893d4c6](https://github.com/revanced/revanced-patcher/commit/893d4c699bad4c70002fc691c261447d01948b5c))
* `PatchLoader` ([ec9fd15](https://github.com/revanced/revanced-patcher/commit/ec9fd15f9b9b9968be7fb5cb384eb8ee2a0c9ba3))
* Add `findParentMethod` utility method ([#4](https://github.com/revanced/revanced-patcher/issues/4)) ([bbb2c54](https://github.com/revanced/revanced-patcher/commit/bbb2c547aae8dd774a1a883de24fe45da463fa35))
* add `MethodWalker` ([7755bbc](https://github.com/revanced/revanced-patcher/commit/7755bbc645773e49053fb9ad2b6fd18a7f488659))
* add `p` naming scheme to smali compiler ([79909cf](https://github.com/revanced/revanced-patcher/commit/79909cf260c0578e88ad22d63397957dbaa91702))
* add extensions for cloning methods ([01bfbd6](https://github.com/revanced/revanced-patcher/commit/01bfbd656ee06cb2cab951c43d7f76a465a40830))
* add findClass method with className ([4087f49](https://github.com/revanced/revanced-patcher/commit/4087f498638ee88ba3eaca792039fe481f404732))
* Add first tests ([544bcf7](https://github.com/revanced/revanced-patcher/commit/544bcf76bd8a8c790c2f799606ad8c9ac7d2aa82))
* add fuzzy resolver ([7a56dca](https://github.com/revanced/revanced-patcher/commit/7a56dca004cd793121a59ea854c77f4c1a01bd6f))
* add immutableMethod ([c63b20f](https://github.com/revanced/revanced-patcher/commit/c63b20fa65aba8bb060a4a7a652747cba7198c2b))
* add inline smali compiler ([bfe4e3e](https://github.com/revanced/revanced-patcher/commit/bfe4e3e298ac963936ca9621e12aefbe56260826))
* add missing test for fields ([6b8b057](https://github.com/revanced/revanced-patcher/commit/6b8b0573d479e227b45dc36a6abac622c3ccebdd))
* add or extension for AccessFlags ([00c85b5](https://github.com/revanced/revanced-patcher/commit/00c85b5d750ccc8de69ad4101220b19eeaf99bcb))
* Add patch metadata ([642e903](https://github.com/revanced/revanced-patcher/commit/642e9031eb3727ebdca22c75b7c5c602a8775da0)), closes [ReVancedTeam/revanced-patches#1](https://github.com/ReVancedTeam/revanced-patches/issues/1)
* add SafeClassWriter ([6626014](https://github.com/revanced/revanced-patcher/commit/6626014ef3dde2f98a53f75d71eeb0de85189bf3))
* Add warnings for Fuzzy resolver ([715a2ad](https://github.com/revanced/revanced-patcher/commit/715a2ad025d127b5a8225ce50202a859f53c7f50))
* allow classes to be overwritten in addFiles and resolve signatures when applyPatches is called ([1db735b](https://github.com/revanced/revanced-patcher/commit/1db735b1e2b570bdb1ddce0b9cd724c580113a84))
* Allow unknown opcodes using `null` ([0e5f4ba](https://github.com/revanced/revanced-patcher/commit/0e5f4ba2d55288415c4d1be70ab6a8ab8c1c0d10))
* Finish first patcher test ([0d8d19e](https://github.com/revanced/revanced-patcher/commit/0d8d19e708a47315e28e7493618568ea40f1e062))
* Improve `SignatureResolver` ([139a23b](https://github.com/revanced/revanced-patcher/commit/139a23b7500a2d2577df47caf3fd0c5ec891a8d8))
* migrate to `DexPatchBundle` and `JarPatchBundle` ([8615798](https://github.com/revanced/revanced-patcher/commit/8615798711185b30ce622d9d09faba21f3a92f97))
* migrate to dexlib ([3651981](https://github.com/revanced/revanced-patcher/commit/36519811610192e299834e9d00627a94faad56a9))
* Minor refactor and return proxy, if class has been proxied already ([4b26305](https://github.com/revanced/revanced-patcher/commit/4b26305bd57ba9e3eb3e34218ffe10d6c5a2f598))
* optional `forStaticMethod` parameter for `InlineSmaliCompiler.compileMethodInstructions` ([41e8860](https://github.com/revanced/revanced-patcher/commit/41e88605c33d1f0d9e7f5466cac03a3b339afb82))
* patch dependencies annotation and `PatcherOptions` ([6c65952](https://github.com/revanced/revanced-patcher/commit/6c65952d80a795a3ef4a37877123e9375025d3ae))
* properly manage `ClassProxy` & add `ProxyBackedClassList` ([6cb1fdf](https://github.com/revanced/revanced-patcher/commit/6cb1fdf6171e1ab75b7ee28163965eacc00cc5a0))
* remaining mutable `EncodedValue` classes ([3f97cc8](https://github.com/revanced/revanced-patcher/commit/3f97cc8e1fa10546d7069e01e5e66a537b0d6f7e))
* string signature ([#22](https://github.com/revanced/revanced-patcher/issues/22)) ([612515a](https://github.com/revanced/revanced-patcher/commit/612515acf8539febf952f258d30aa3d4b631e3b7))
* use annotations instead of metadata objects ([d20f7fd](https://github.com/revanced/revanced-patcher/commit/d20f7fd6e1ede6ec7baccb1500ab3fc66d78df73))
* utility functions to get metadata of patch & sigs ([54511a4](https://github.com/revanced/revanced-patcher/commit/54511a4fc6417d7fe0c868d441e7d6b0ec9e218d))


### Performance Improvements

* check type instead of class ([c7ef264](https://github.com/revanced/revanced-patcher/commit/c7ef2644d83e1d8e84decb0631a6549d394180fc))
* decode manifest only when not using resource patcher ([4f60bea](https://github.com/revanced/revanced-patcher/commit/4f60bea81e0bbe85dc6c3150238980292a1e52ab))
* depend on `androlib` instead of `ApkDecoder` ([cc9416d](https://github.com/revanced/revanced-patcher/commit/cc9416dd11b66140c2882021cbe5088659d85371))
* do not resolve empty signatures list ([b1eebc9](https://github.com/revanced/revanced-patcher/commit/b1eebc99a71269df33c37f35c1f56ea20a9d6bc0))
* lazy-ify all mutable clones ([d18a3b6](https://github.com/revanced/revanced-patcher/commit/d18a3b6a28cae4fcb1c4986903208298ee50b083))
* optimize indexOf call away ([9991f39](https://github.com/revanced/revanced-patcher/commit/9991f39c9a4fa22a221aab0bbf9e08ca7f967fa9))
* use Set instead of List since there are no dupes ([e65ebd2](https://github.com/revanced/revanced-patcher/commit/e65ebd27c250b1735acf73af0f6b03274b0137f6))
* use String List and compare instead of any lambda ([5bd416b](https://github.com/revanced/revanced-patcher/commit/5bd416b409290906a6378344f70391e8692ae27f))


### Reverts

* AccessFlag extensions not working with IDE ([0bfb92a](https://github.com/revanced/revanced-patcher/commit/0bfb92a0cbd72df5ba513264efb583e201cfcf82))
* previous commits check for dupes in dexFile, not cache ([e810197](https://github.com/revanced/revanced-patcher/commit/e810197e2aa64534f2e8637165d884cbefbce8ae))


### BREAKING CHANGES

* arrayOf has to be changed to listOf.
* Method signature of Patcher#save() was changed to comply with the changes of multidexlib2.
* Removed usage of ASM library
* Array<Int> was changed to IntArray. This breaks existing patches.
* Package name was changed from "net.revanced" to "app.revanced"
* Method signature of execute() was changed to include the cache, this will break existing implementations of the Patch class.
* Patch class is now an abstract class. You must implement it. You can use anonymous implements, like done in the tests.

# [1.0.0-dev.18](https://github.com/revanced/revanced-patcher/compare/v1.0.0-dev.17...v1.0.0-dev.18) (2022-06-04)


### Features

* `Dependencies` annotation ([83d608a](https://github.com/revanced/revanced-patcher/commit/83d608ac06a7d5ceb31b6e0022b501d99edb63a3))
* optional `forStaticMethod` parameter for `InlineSmaliCompiler.compileMethodInstructions` ([28b9847](https://github.com/revanced/revanced-patcher/commit/28b98478e4e8e8f238e82f7fa2307aeb1547955d))

# [1.0.0-dev.17](https://github.com/revanced/revanced-patcher/compare/v1.0.0-dev.16...v1.0.0-dev.17) (2022-05-31)


### Features

* patch dependencies annotation and `PatcherOptions` ([8442991](https://github.com/revanced/revanced-patcher/commit/84429912900872405b44804943357dda8430a550))

# [1.0.0-dev.16](https://github.com/revanced/revanced-patcher/compare/v1.0.0-dev.15...v1.0.0-dev.16) (2022-05-27)


### Bug Fixes

* `JarPatchBundle` loading non-class files to class loader ([3f0c740](https://github.com/revanced/revanced-patcher/commit/3f0c740200dd91a060426638c2f8f516938b4c53))
* remove dependency to fork of Apktool ([0fa529f](https://github.com/revanced/revanced-patcher/commit/0fa529fcdf9a7b5ea9a361b9f9f32f3f3fce009f))


### Features

* migrate to `DexPatchBundle` and `JarPatchBundle` ([7573db2](https://github.com/revanced/revanced-patcher/commit/7573db25757de89824af4f3aea167e500120eabb))

# [1.0.0-dev.15](https://github.com/revanced/revanced-patcher/compare/v1.0.0-dev.14...v1.0.0-dev.15) (2022-05-25)


### Features

* utility functions to get metadata of patch & sigs ([72f16b7](https://github.com/revanced/revanced-patcher/commit/72f16b778587c28d8f8e91da502f197e7dc35d6d))

# [1.0.0-dev.14](https://github.com/revanced/revanced-patcher/compare/v1.0.0-dev.13...v1.0.0-dev.14) (2022-05-24)


### Bug Fixes

* reformat (trigger release) ([45a167e](https://github.com/revanced/revanced-patcher/commit/45a167e7856da0306f796953775c7b7543d9bec0))

# [1.0.0-dev.13](https://github.com/revanced/revanced-patcher/compare/v1.0.0-dev.12...v1.0.0-dev.13) (2022-05-24)


### Performance Improvements

* decode manifest only when not using resource patcher ([40b1fa4](https://github.com/revanced/revanced-patcher/commit/40b1fa43e1704ace29d3e349df2f4a8ea828c5c2))

# [1.0.0-dev.12](https://github.com/revanced/revanced-patcher/compare/v1.0.0-dev.11...v1.0.0-dev.12) (2022-05-22)


### Bug Fixes

* using old instance of `Androlib` when saving ([5630e49](https://github.com/revanced/revanced-patcher/commit/5630e4966310311cdfd53e2ba128255047626adc))

# [1.0.0-dev.11](https://github.com/revanced/revanced-patcher/compare/v1.0.0-dev.10...v1.0.0-dev.11) (2022-05-22)


### Features

* `PatchLoader` ([1a99eca](https://github.com/revanced/revanced-patcher/commit/1a99ecaffe5e55977655316e68b014fdeba374a1))
* use annotations instead of metadata objects ([6726884](https://github.com/revanced/revanced-patcher/commit/6726884be5af56b6856749e73fb9f4f97559854a))

# [1.0.0-dev.10](https://github.com/revanced/revanced-patcher/compare/v1.0.0-dev.9...v1.0.0-dev.10) (2022-05-07)


### Bug Fixes

* qualifying `Element` with wrong package ([4d74de4](https://github.com/revanced/revanced-patcher/commit/4d74de4061f26c0d7c17fabd849051b429d86033))

# [1.0.0-dev.9](https://github.com/revanced/revanced-patcher/compare/v1.0.0-dev.8...v1.0.0-dev.9) (2022-05-07)


### Bug Fixes

* `compareSignatureToMethod` not matching correctly in case opcodes are null ([5ae5e98](https://github.com/revanced/revanced-patcher/commit/5ae5e98f1f8e174d800bcc75723e1ed965d66196))
* `ConcurrentModificationException` while iterating through `proxies` and modifying it ([bfeeaf4](https://github.com/revanced/revanced-patcher/commit/bfeeaf443549c9a43279d83a0628c061a382beb9))
* `PackageMetadata` ([305a817](https://github.com/revanced/revanced-patcher/commit/305a81793a9a04fe4e8969f2d3b591b0f01e3b63))
* `replaceWith` not replacing classes with used class proxies ([f0f3403](https://github.com/revanced/revanced-patcher/commit/f0f34031dd4e618223f016f7c427d7c93ab8456a))
* adding existing classes to the patchers cache ([4281546](https://github.com/revanced/revanced-patcher/commit/4281546f69225ee90ec4c003f4313df41edf71a6))
* always return PatchResultSuccess on patch success ([866b03a](https://github.com/revanced/revanced-patcher/commit/866b03af217ad97dd2755bfdc0ffe5bcf723c949))
* applying no patches throwing error ([f88c118](https://github.com/revanced/revanced-patcher/commit/f88c11820dbdc0d1d52a49c9bcdb4f7caa9eb6eb))
* applyPatches not returning successful patches ([8b70bb4](https://github.com/revanced/revanced-patcher/commit/8b70bb42909434a5e59315502f6d54d7c7691f18))
* Classes not being written properly because of array shifting ([1471956](https://github.com/revanced/revanced-patcher/commit/147195647c3990ab78ba95e4b3000650e718b713))
* failing tests temporarily ([66b08f8](https://github.com/revanced/revanced-patcher/commit/66b08f8b3a8f31844c7e7bab4df4243521d4a431))
* fix classes having multiple instances of fields ([b711b80](https://github.com/revanced/revanced-patcher/commit/b711b8001e4845857fa6cc71b107f1c553b31e80))
* fix classes having multiple method instances ([12c10d8](https://github.com/revanced/revanced-patcher/commit/12c10d8c64422c4534c23467e367707e3b953f82))
* Fixed writer & signature resolver, improved tests & speed, minor refactoring ([bb42fa3](https://github.com/revanced/revanced-patcher/commit/bb42fa3c6f59b78a7223fc70edbe598ec181ee37))
* fuzzy resolver warning params were turned around ([d49df10](https://github.com/revanced/revanced-patcher/commit/d49df10a3ca6b472ce4a32d10cfe787ca243d47b))
* incorrect pattern offset ([03700ff](https://github.com/revanced/revanced-patcher/commit/03700ffa519e5f20b1a0d0ffe68f3fb504351ee5))
* make `methodMetadata` nullable in `MethodSignatureMetadata` ([864e38c](https://github.com/revanced/revanced-patcher/commit/864e38c06906a9e29271fe383d51a8ec6594a46c))
* make warnings nullable instead of lateinit ([04b49b8](https://github.com/revanced/revanced-patcher/commit/04b49b8b664e45e64e9561eca3353ffdeda91187))
* match to correct signature method parameters ([c49071a](https://github.com/revanced/revanced-patcher/commit/c49071aff78245f27c98a4760b361c30aa6340bc))
* MethodSignature#resolved throwing an exception ([82b1e66](https://github.com/revanced/revanced-patcher/commit/82b1e66d54bed1e4c335e0515b7ff3ec901fa6f8))
* Move proxy package out of cache package ([6bc4e7e](https://github.com/revanced/revanced-patcher/commit/6bc4e7eab742f5796f3041332c70495e3f993c9b))
* null check causing an exception ([560c485](https://github.com/revanced/revanced-patcher/commit/560c485ab08b08a213b58704b11b1e2f5f625080))
* Patcher not writing resolved methods ([d15240d](https://github.com/revanced/revanced-patcher/commit/d15240d0330a63c4b568fc5de3de861b8046cba4))
* reaching all constructors not possible ([37fa994](https://github.com/revanced/revanced-patcher/commit/37fa9949ec84ffd277f32b1cd554e92be41d35e4))
* remove leftover debug code ([4458141](https://github.com/revanced/revanced-patcher/commit/4458141d6d2e1b015c0d70a6e65e6c32a3cf17dc))
* return mutable set of classes ([84bc7e0](https://github.com/revanced/revanced-patcher/commit/84bc7e0dc76f0732613383accb803f2c52da98ac))
* returning failure on success ([3b68d5c](https://github.com/revanced/revanced-patcher/commit/3b68d5c65ec3082d1aa48525b4ee2a4163895a3b))
* Search method map for existing class proxy ([d5e694c](https://github.com/revanced/revanced-patcher/commit/d5e694c306a47f47b8d1078b5c9f8a742445cf7e))
* string signature in `SignatureResolver` ([ac36d19](https://github.com/revanced/revanced-patcher/commit/ac36d19693390db8f404ed30963aefb2fb7519e0))
* Suppress unused for addFiles ([a0d6d46](https://github.com/revanced/revanced-patcher/commit/a0d6d462170552929039d71eafa813fdfde215cb))
* throwing in case the opcode patterns do not match ([f72dd68](https://github.com/revanced/revanced-patcher/commit/f72dd68ec575ee0926ee668911ebb6f85b75f7d1))
* use Array instead of Iterable for methodParameters ([312235b](https://github.com/revanced/revanced-patcher/commit/312235b194cac01ddc3f03ecff32c7de4e48c29c))
* write all classes ([6ad51aa](https://github.com/revanced/revanced-patcher/commit/6ad51aad9a94d8dd5afb5e270138ef7161ccfb07))


### Code Refactoring

* bump multidexlib2 to 2.5.2.r2 ([32e6458](https://github.com/revanced/revanced-patcher/commit/32e645850d4cc74aa708984da03ae1606e696d20))
* Change all references from Array to Iterable ([264989f](https://github.com/revanced/revanced-patcher/commit/264989f48804ed637469436acf8165ac4b7be383))


### Features

* add `MethodWalker` ([659e108](https://github.com/revanced/revanced-patcher/commit/659e1087c9e7a33e04cd7eb728c01ed946335810))
* add `p` naming scheme to smali compiler ([38556d6](https://github.com/revanced/revanced-patcher/commit/38556d61ab192dfa84083d935ee3e9eee5450d06))
* add extensions for cloning methods ([df7503b](https://github.com/revanced/revanced-patcher/commit/df7503b47b1e2162d6ab666f8586c633c314016f))
* add findClass method with className ([78235d1](https://github.com/revanced/revanced-patcher/commit/78235d1abe267e6aaa086662ad69af7132b8ff74))
* Add first tests ([6767c8f](https://github.com/revanced/revanced-patcher/commit/6767c8fbc15ea18a61db53e1472483632077f62a))
* add fuzzy resolver ([a492808](https://github.com/revanced/revanced-patcher/commit/a4928080217451017a99cf158fd5cc9d650a5a9e))
* add immutableMethod ([eed1cfd](https://github.com/revanced/revanced-patcher/commit/eed1cfda7b89f03f4c61ac4401707e1a12e6efb3))
* add inline smali compiler ([dbafe2a](https://github.com/revanced/revanced-patcher/commit/dbafe2ab37b25480f3e218d94ced5af2e56cba68))
* add missing test for fields ([4022b8b](https://github.com/revanced/revanced-patcher/commit/4022b8b847e8767ace0da3f98ad72ab61a4c242b))
* add or extension for AccessFlags ([aec5eeb](https://github.com/revanced/revanced-patcher/commit/aec5eeb597f0e9968b43efa228c96e83175e031c))
* Add patch metadata ([8544fc4](https://github.com/revanced/revanced-patcher/commit/8544fc4cbcb5d7c1ac0f6fcae52882a00d2bacf5)), closes [ReVancedTeam/revanced-patches#1](https://github.com/ReVancedTeam/revanced-patches/issues/1)
* Add warnings for Fuzzy resolver ([643a14e](https://github.com/revanced/revanced-patcher/commit/643a14e664c7ff86580da683eaff9c486884ee2c))
* allow classes to be overwritten in addFiles and resolve signatures when applyPatches is called ([5f71a34](https://github.com/revanced/revanced-patcher/commit/5f71a342ac9c6aa64a4983156f595ae0832c30e8))
* Allow unknown opcodes using `null` ([f4a47d4](https://github.com/revanced/revanced-patcher/commit/f4a47d4dc893bb511ca2087a1a63bfc35888663f))
* Finish first patcher test ([a9e4e8a](https://github.com/revanced/revanced-patcher/commit/a9e4e8ac3203bdd62abcd1e366f08a2269919571))
* Improve `SignatureResolver` ([88a6a27](https://github.com/revanced/revanced-patcher/commit/88a6a2730296883e191543c2666f39f24c05d74d))
* migrate to dexlib ([be51f42](https://github.com/revanced/revanced-patcher/commit/be51f42710c1489ef4405700e56ffecee5e6552f))
* Minor refactor and return proxy, if class has been proxied already ([2d3c611](https://github.com/revanced/revanced-patcher/commit/2d3c61113dc9b76c43e93928ba11026fe0ad444e))
* properly manage `ClassProxy` & add `ProxyBackedClassList` ([2319787](https://github.com/revanced/revanced-patcher/commit/23197879b20906aac7563e5f8107305edd7ccb1b))
* remaining mutable `EncodedValue` classes ([7d38bb0](https://github.com/revanced/revanced-patcher/commit/7d38bb0baaeabade6a9e64d97e2dd6c20edd153f))
* string signature ([#22](https://github.com/revanced/revanced-patcher/issues/22)) ([c245edb](https://github.com/revanced/revanced-patcher/commit/c245edb0c5317c1bb884ea315a1a04b720f20dd5))


### Performance Improvements

* depend on `androlib` instead of `ApkDecoder` ([e5c054a](https://github.com/revanced/revanced-patcher/commit/e5c054ac2f68b00ac123a45ed56b9f150332a82d))
* do not resolve empty signatures list ([1f7bf3a](https://github.com/revanced/revanced-patcher/commit/1f7bf3ac6c77a71abd687f2ff6f7306a40654a1b))
* lazy-ify all mutable clones ([05e4400](https://github.com/revanced/revanced-patcher/commit/05e44007d81399791aa1bab1eead66b7ff662043))
* optimize indexOf call away ([f8e978a](https://github.com/revanced/revanced-patcher/commit/f8e978af888255d9c104a8275be1d9b091af3f96))
* use Set instead of List since there are no dupes ([6221387](https://github.com/revanced/revanced-patcher/commit/622138736dca6c0161171330801b7b5666594ec7))
* use String List and compare instead of any lambda ([aed4fd9](https://github.com/revanced/revanced-patcher/commit/aed4fd9a3c9e7f96c1e2c54b831c3fe7d3d720a2))


### Reverts

* AccessFlag extensions not working with IDE ([e161f7f](https://github.com/revanced/revanced-patcher/commit/e161f7fea449883b7ac0fb436ed4f7f2ff78af62))
* previous commits check for dupes in dexFile, not cache ([433914f](https://github.com/revanced/revanced-patcher/commit/433914feda3066102a073d6b3bc457d0fae87911))


### BREAKING CHANGES

* arrayOf has to be changed to listOf.
* Method signature of Patcher#save() was changed to comply with the changes of multidexlib2.
* Removed usage of ASM library

# [1.0.0-dev.8](https://github.com/ReVancedTeam/revanced-patcher/compare/v1.0.0-dev.7...v1.0.0-dev.8) (2022-03-24)


### Performance Improvements

* check type instead of class ([47eb493](https://github.com/ReVancedTeam/revanced-patcher/commit/47eb493f5425dc27a4d6e79e6b02a36ef760e8da))

# [1.0.0-dev.7](https://github.com/ReVancedTeam/revanced-patcher/compare/v1.0.0-dev.6...v1.0.0-dev.7) (2022-03-24)


### Bug Fixes

* **MethodResolver:** fix cd57a8c9a0db7e3ae5ad0bca202e5955930319ab ([1af31b2](https://github.com/ReVancedTeam/revanced-patcher/commit/1af31b2aa3772a7473c04d27bf835c8eae13438d))

# [1.0.0-dev.6](https://github.com/ReVancedTeam/revanced-patcher/compare/v1.0.0-dev.5...v1.0.0-dev.6) (2022-03-24)


### Bug Fixes

* **MethodResolver:** strip labels nodes so opcode patterns match ([cd57a8c](https://github.com/ReVancedTeam/revanced-patcher/commit/cd57a8c9a0db7e3ae5ad0bca202e5955930319ab))

# [1.0.0-dev.5](https://github.com/ReVancedTeam/revanced-patcher/compare/v1.0.0-dev.4...v1.0.0-dev.5) (2022-03-24)


### Bug Fixes

* **MethodResolver:** strip labels and line numbers so opcode patterns match ([8d1bb5f](https://github.com/ReVancedTeam/revanced-patcher/commit/8d1bb5f3d9da544cf6e3e3848bfcc56327cde810))

# [1.0.0-dev.4](https://github.com/ReVancedTeam/revanced-patcher/compare/v1.0.0-dev.3...v1.0.0-dev.4) (2022-03-23)


### Bug Fixes

* give ClassWriter a ClassReader for symtable ([e8f6973](https://github.com/ReVancedTeam/revanced-patcher/commit/e8f6973938c70002f04a86f329aa5b134f6ef649))

# [1.0.0-dev.3](https://github.com/ReVancedTeam/revanced-patcher/compare/v1.0.0-dev.2...v1.0.0-dev.3) (2022-03-23)


### Features

* add SafeClassWriter ([ca6b94d](https://github.com/ReVancedTeam/revanced-patcher/commit/ca6b94d943b7067aae87a4e282cfb323811c0462))

# [1.0.0-dev.2](https://github.com/ReVancedTeam/revanced-patcher/compare/v1.0.0-dev.1...v1.0.0-dev.2) (2022-03-23)


### Bug Fixes

* set marklimit to Integer.MAX_VALUE ([ab6453c](https://github.com/ReVancedTeam/revanced-patcher/commit/ab6453ca8a02af70da4468c1a63c68dde4d392ef))

# 1.0.0-dev.1 (2022-03-23)


### Bug Fixes

* avoid ignoring test resources (fixes [#1](https://github.com/ReVancedTeam/revanced-patcher/issues/1)) ([d5a3c76](https://github.com/ReVancedTeam/revanced-patcher/commit/d5a3c76389ba902c22ddc8b7ba1a110b7ff852df))
* current must be calculated after increment ([5f12bab](https://github.com/ReVancedTeam/revanced-patcher/commit/5f12bab5df97fbe6e2e62c1bf2814a2e682ab4f3))
* **gradle:** publish source and javadocs ([87bbde5](https://github.com/ReVancedTeam/revanced-patcher/commit/87bbde5e06d038d8f6ddaac391e1db397f5a5590))
* **Io:** fix finding classes by name ([460d62a](https://github.com/ReVancedTeam/revanced-patcher/commit/460d62a24c4cad05691c4b269c2faeda47fee3b7))
* **Io:** JAR loading and saving ([#8](https://github.com/ReVancedTeam/revanced-patcher/issues/8)) ([4d98cbc](https://github.com/ReVancedTeam/revanced-patcher/commit/4d98cbc9e8fe1e39b3d9d4185b3c5b4882093af6))
* nullable signature members ([#10](https://github.com/ReVancedTeam/revanced-patcher/issues/10)) ([8db8893](https://github.com/ReVancedTeam/revanced-patcher/commit/8db8893ab1bda55f11cc75db55c7c1a38f1d1b16))
* Patch should have access to the Cache ([6c0f082](https://github.com/ReVancedTeam/revanced-patcher/commit/6c0f0823c91dc643dd80205b1e840e59827bee06))
* remove broken code ([0e72a6e](https://github.com/ReVancedTeam/revanced-patcher/commit/0e72a6e85ff9a6035510680fc5e33ab0cd14144f))
* set index for insertAt to 0 by default ([1769132](https://github.com/ReVancedTeam/revanced-patcher/commit/1769132a9e29cf3a0c5ae0917209c83c138c0216))
* workflow on dev branch ([7e67daf](https://github.com/ReVancedTeam/revanced-patcher/commit/7e67daf8789c534bed0091a3975776eb95039acc))


### Code Refactoring

* convert Patch to abstract class ([23e897a](https://github.com/ReVancedTeam/revanced-patcher/commit/23e897a7a9125f4ac4266263e7dd94fe63a0bfa1))
* Optimize Signature class ([#11](https://github.com/ReVancedTeam/revanced-patcher/issues/11)) ([49beec9](https://github.com/ReVancedTeam/revanced-patcher/commit/49beec9fc6eee6ccf52a6185761a200a6ed2b16e))
* Rename `net.revanced` to `app.revanced` ([3ab42a9](https://github.com/ReVancedTeam/revanced-patcher/commit/3ab42a932c8d5027d554106dfe8e1299ebc1ac7f))


### Features

* Add `findParentMethod` utility method ([#4](https://github.com/ReVancedTeam/revanced-patcher/issues/4)) ([00c6ab7](https://github.com/ReVancedTeam/revanced-patcher/commit/00c6ab7fafe2a59dec0052cc5b7d1d16939076b2))


### BREAKING CHANGES

* Array<Int> was changed to IntArray. This breaks existing patches.
* Package name was changed from "net.revanced" to "app.revanced"
* Method signature of execute() was changed to include the cache, this will break existing implementations of the Patch class.
* Patch class is now an abstract class. You must implement it. You can use anonymous implements, like done in the tests.
