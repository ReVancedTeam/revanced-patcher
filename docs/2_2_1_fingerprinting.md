<p align="center">
  <picture>
    <source
      width="256px"
      media="(prefers-color-scheme: dark)"
      srcset="../assets/revanced-headline/revanced-headline-vertical-dark.svg"
    >
    <img 
      width="256px"
      src="../assets/revanced-headline/revanced-headline-vertical-light.svg"
    >
  </picture>
  <br>
  <a href="https://revanced.app/">
     <picture>
         <source height="24px" media="(prefers-color-scheme: dark)" srcset="../assets/revanced-logo/revanced-logo.svg" />
         <img height="24px" src="../assets/revanced-logo/revanced-logo.svg" />
     </picture>
   </a>&nbsp;&nbsp;&nbsp;
   <a href="https://github.com/ReVanced">
       <picture>
           <source height="24px" media="(prefers-color-scheme: dark)" srcset="https://i.ibb.co/dMMmCrW/Git-Hub-Mark.png" />
           <img height="24px" src="https://i.ibb.co/9wV3HGF/Git-Hub-Mark-Light.png" />
       </picture>
   </a>&nbsp;&nbsp;&nbsp;
   <a href="http://revanced.app/discord">
       <picture>
           <source height="24px" media="(prefers-color-scheme: dark)" srcset="https://user-images.githubusercontent.com/13122796/178032563-d4e084b7-244e-4358-af50-26bde6dd4996.png" />
           <img height="24px" src="https://user-images.githubusercontent.com/13122796/178032563-d4e084b7-244e-4358-af50-26bde6dd4996.png" />
       </picture>
   </a>&nbsp;&nbsp;&nbsp;
   <a href="https://reddit.com/r/revancedapp">
       <picture>
           <source height="24px" media="(prefers-color-scheme: dark)" srcset="https://user-images.githubusercontent.com/13122796/178032351-9d9d5619-8ef7-470a-9eec-2744ece54553.png" />
           <img height="24px" src="https://user-images.githubusercontent.com/13122796/178032351-9d9d5619-8ef7-470a-9eec-2744ece54553.png" />
       </picture>
   </a>&nbsp;&nbsp;&nbsp;
   <a href="https://t.me/app_revanced">
      <picture>
         <source height="24px" media="(prefers-color-scheme: dark)" srcset="https://user-images.githubusercontent.com/13122796/178032213-faf25ab8-0bc3-4a94-a730-b524c96df124.png" />
         <img height="24px" src="https://user-images.githubusercontent.com/13122796/178032213-faf25ab8-0bc3-4a94-a730-b524c96df124.png" />
      </picture>
   </a>&nbsp;&nbsp;&nbsp;
   <a href="https://x.com/revancedapp">
      <picture>
         <source media="(prefers-color-scheme: dark)" srcset="https://user-images.githubusercontent.com/93124920/270180600-7c1b38bf-889b-4d68-bd5e-b9d86f91421a.png">
         <img height="24px" src="https://user-images.githubusercontent.com/93124920/270108715-d80743fa-b330-4809-b1e6-79fbdc60d09c.png" />
      </picture>
   </a>&nbsp;&nbsp;&nbsp;
   <a href="https://www.youtube.com/@ReVanced">
      <picture>
         <source height="24px" media="(prefers-color-scheme: dark)" srcset="https://user-images.githubusercontent.com/13122796/178032714-c51c7492-0666-44ac-99c2-f003a695ab50.png" />
         <img height="24px" src="https://user-images.githubusercontent.com/13122796/178032714-c51c7492-0666-44ac-99c2-f003a695ab50.png" />
     </picture>
   </a>
   <br>
   <br>
   Continuing the legacy of Vanced
</p>

# 🔎 Fingerprinting

In the context of ReVanced, a fingerprint is a partial description of a method.
It is used to uniquely match a method by its characteristics.
Fingerprinting is used to match methods with a limited amount of known information.
Methods with obfuscated names that change with each update are primary candidates for fingerprinting.
The goal of fingerprinting is to uniquely identify a method by capturing various attributes, such as the return type,
access flags, instructions, strings, and more.

## 🔎 Example target Java code and bytecode

```java
package com.some.app.ads;

class AdsLoader {
  private final static Map<String, String> a = new HashMap<>();

  // Method to fingerprint    
  public final boolean obfuscatedMethod(String parameter1, int parameter2, ObfuscatedClass parameter3) {
    // Filter 1 target instruction.
    String value1 = a.get(parameter1);

    unrelatedMethod(value1);

    // Filter 2, 3, 4 target instructions, and the instructions to modify.
    if ("showBannerAds".equals(value1)) {
      showBannerAds();
    }

    // Filter 5 and 6 target instructions.
    return parameter2 != 1337;
  }

  private void showBannerAds() {
    // ...
  }

  private void unrelatedMethod(String parameter) {
    // ...
  }
}
```

```asm
# Method to fingerprint
.method public final obfuscatedMethod(Ljava/lang/String;ILObfuscatedClass;)Z
    .registers 4

    # Filter 1 target instruction.
    sget-object v0, Lcom/some/app/ads/AdsLoader;->a:Ljava/util/Map;

    invoke-interface {v0, p1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/String;

    invoke-direct {p0, p1}, Lcom/some/app/ads/AdsLoader;->unrelatedMethod(Ljava/lang/String;)V

    # Filter 2 target instruction.
    const-string v0, "showBannerAds"

    # Filter 3 target instruction.
    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    # Filter 4 target instruction.
    move-result p1

    if-eqz p1, :cond_16

    invoke-direct {p0}, Lcom/some/app/ads/AdsLoader;->showBannerAds()V

    # Filter 5 target instruction.
    :cond_16
    const/16 p1, 0x539

    # Filter 6 target instruction.
    if-eq p2, p1, :cond_1c

    const/4 p1, 0x1

    goto :goto_1d

    :cond_1c
    const/4 p1, 0x0

    :goto_1d
    return p1
.end method
```

## ⛳️ Example fingerprint

```kt
val hideAdsFingerprint by fingerprint {
    // Method signature:
    accessFlags(AccessFlags.PUBLIC, AccessFlags.FINAL)
    returns("Z")
    // Last parameter is simply `L` since it's an obfuscated class.
    parameters("Ljava/lang/String;", "I", "L")
    
    // Method implementation:
    instructions( 
        // Filter 1
        fieldAccess(
            definingClass = "this",
            type = "Ljava/util/Map;"
        ),

        // Filter 2 
        string("showBannerAds"),
      
        // Filter 3 
        methodCall(
            definingClass = "Ljava/lang/String;",
            name = "equals",
        ),

        // Filter 4
        // maxInstructionsBefore = 0 means this must match immediately after the last filter.
        opcode(Opcode.MOVE_RESULT, maxInstructionsBefore = 0),

        // Filter 5
        literal(1337),
        
        // Filter 6 
        opcode(Opcode.IF_EQ),
    )
    custom { method, classDef ->
        classDef.type == "Lcom/some/app/ads/AdsLoader;"
    }
}
```

  Notice the instruction filters do not declare every instruction in the target method,
  and between each filter can exist 0 or more other instructions.  Instruction filters
  must be declared in the same order as the instructions appear in the target method.

  If the distance between each instruction declaration can be approximated,
  then the `maxInstructionsBefore` parameter can be used to restrict the instruction match to
  a maximum distance from the last instruction.  A value of 0 for the first instruction filter
  means the filter must be the first instruction of the target method. To restrict an instruction
  filter to only match the last instruction of a method, use the `lastInstruction()` filter wrapper.

  If a single instruction varies slightly between different app targets but otherwise the fingerprint
  is still the same, the `anyInstruction()` wrapper can be used to specify variations of the
  same instruction.  Such as:
  `anyInstruction(string("string in early app target"), string("updated string in latest app target"))`

> [!TIP]
> A fingerprint should contain information about a method likely to remain stable across updates.
> Names of obfuscated classes and methods should not be used since they can change between app updates.

## 🔨 How to use fingerprints

After declaring a fingerprint it can be used in a patch to find the method it matches to:

```kt
execute {
  hideAdsFingerprint.let {
    // Changes the target code to:
    // if (false) {
    //    showBannerAds();
    // }
    val filter4 = it.instructionMatches[3]
    val moveResultIndex = filter3.index
    val moveResultRegister = filter3.getInstruction<OneRegisterInstruction>().registerA
     
    it.method.addInstructions(moveResultIndex + 1, "const/4 v$moveResultRegister, 0x0")
  }
}
```

Be careful if making more than 1 modification to the same method.  Adding/removing instructions to
a method can cause fingerprint match indexes to no longer be correct. The simplest solution is
to modify the target method from the last match index to the first.

Modifying the example above to also change the code `return parameter2 != 1337;` into: `return false;`: 

```kt
execute {
  appFingerprint.let {
    // Modify method from last indexes to first to preserve the correct fingerprint indexes.
      
    // Remove conditional branch and always return false.
    val filter6 = it.instructionMatches[5]
    it.method.removeInstruction(filter6.index)

    
    // Changes the target code to:
    // if (false) {
    //    showBannerAds();
    // }
    val filter4 = it.instructionMatches[3]
    val moveResultIndex = filter3.index
    val moveResultRegister = filter3.getInstruction<OneRegisterInstruction>().registerA
     
    it.method.addInstructions(moveResultIndex + 1, "const/4 v$moveResultRegister, 0x0")
  }
}
```

For performance reasons, a fingerprint will always match only once.
This makes it useful to share fingerprints between multiple patches,
and let the first executing patch match the fingerprint:

```kt
// Either of these two patches will match the fingerprint first and the other patch can reuse the match:
val mainActivityPatch1 = bytecodePatch {
  execute {
    mainActivityOnCreateFingerprint.method
  }
}

val mainActivityPatch2 = bytecodePatch {
  execute {
    mainActivityOnCreateFingerprint.method
  }
}
```

> [!WARNING]
> If the fingerprint can not be matched to any method,
> accessing certain properties of the fingerprint will raise an exception.
> Instead, the `orNull` properties can be used to return `null` if no match is found.

The following properties can be accessed in a fingerprint:

- `originalClassDef`: The immutable class definition the fingerprint matches to.
- `originalClassDefOrNull`: The immutable class definition the fingerprint matches to, or null.
- `originalMethod`: The immutable method the fingerprint matches to.
- `originalMethodOrNull`: The immutable method the fingerprint matches to, or null.
- `classDef`: The mutable class the fingerprint matches to.
- `classDefOrNull`: The mutable class the fingerprint matches to, or null.
- `method`: The mutable method the fingerprint matches to. If no match is found, an exception is raised.
- `methodOrNull`: The mutable method the fingerprint matches to, or null.

The difference between the `original` and non-`original` properties is that the `original` properties return the
original class or method definition, while the non-`original` properties return a mutable copy of the class or method.
The mutable copies can be modified. They are lazy properties, so they are only computed
and only then will effectively replace the `original` method or class definition when accessed.

> [!TIP]
> If only read-only access to the class or method is needed,
> the `originalClassDef` and `originalMethod` properties should be used,
> to avoid making a mutable copy of the class or method.

## 🏹 Manually matching fingerprints

By default, a fingerprint is matched automatically against all classes
when one of the fingerprint's properties is accessed.

Instead, the fingerprint can be matched manually using various overloads of a fingerprint's `match` function:

- In a **list of classes**, if the fingerprint can match in a known subset of classes

  If you have a known list of classes you know the fingerprint can match in,
  you can match the fingerprint on the list of classes:

  ```kt
  execute {
    val match = showAdsFingerprint.match(classes)
  }
  ```

- In a **single class**, if the fingerprint can match in a single known class

  If you know the fingerprint can match a method in a specific class, you can match the fingerprint in the class:

  ```kt
  execute {
    val adsLoaderClass = classes.single { it.name == "Lcom/some/app/ads/Loader;" }

    val match = showAdsFingerprint.match(adsLoaderClass)
  }
  ```

  Another common use case is to find the class of the target code by finger printing an easy
  to identify method in that class (especially a method with string constants), then use the class
  found to match a second fingerprint that finds the target method. 

  ```kt
  execute {
    // Match showAdsFingerprint to the class of the ads loader found by adsLoaderClassFingerprint.
    val match = showAdsFingerprint.match(adsLoaderClassFingerprint.originalClassDef)
  }
  ```

> [!TIP]
> To see real-world examples of fingerprints,
> check out the repository for [ReVanced Patches](https://github.com/revanced/revanced-patches).

## ⏭️ What's next

The next page discusses the structure and conventions of patches.

Continue: [📜 Project structure and conventions](3_structure_and_conventions.md)
