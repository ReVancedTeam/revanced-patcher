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

# 🧩 Anatomy of a ReVanced patch

Learn the API to create patches using ReVanced Patcher.

## ⛳️ Example patch

The following example patch disables ads in an app.  
In the following sections, each part of the patch will be explained in detail.

```kt
package app.revanced.patches.ads

val disableAdsPatch = bytecodePatch(
    name = "Disable ads",
    description = "Disable ads in the app.",
) {
    compatibleWith("com.some.app"("1.0.0"))

    // Patches can depend on other patches, executing them first.
    dependsOn(disableAdsResourcePatch)

    // Merge precompiled DEX files into the patched app, before the patch is executed.
    extendWith("disable-ads.rve")

    // Business logic of the patch to disable ads in the app.
    execute {
        // Fingerprint to find the method to patch.
        val showAdsMatch by showAdsFingerprint {
            // More about fingerprints on the next page of the documentation.
        }

        // In the method that shows ads,
        // call DisableAdsPatch.shouldDisableAds() from the extension (precompiled DEX file)
        // to enable or disable ads.
        showAdsMatch.method.addInstructions(
            0,
            """
                invoke-static {}, LDisableAdsPatch;->shouldDisableAds()Z
                move-result v0
                return v0
            """
        )
    }
}
```

> [!TIP]
> To see real-world examples of patches,
> check out the repository for [ReVanced Patches](https://github.com/revanced/revanced-patches).

## 🧩 Patch API

### ⚙️ Patch options

Patches can have options to get and set before a patch is executed.
Options are useful for making patches configurable.
After loading the patches using `PatchLoader`, options can be set for a patch.
Multiple types are already built into ReVanced Patcher and are supported by any application that uses ReVanced Patcher.

To define an option, use the available `option` functions:

```kt
val patch = bytecodePatch(name = "Patch") {
    // Add an inbuilt option and delegate it to a property.
    val value by stringOption(name = "Inbuilt option")

    // Add an option with a custom type and delegate it to a property.
    val string by option<String>(name = "String option")

    execute {
        println(value)
        println(string)
    }
}
```

Options of a patch can be set after loading the patches with `PatchLoader` by obtaining the instance for the patch:

```kt
loadPatchesJar(patches).apply {
    // Type is checked at runtime.
    first { it.name == "Patch" }.options["Option"] = "Value"
}
```

The type of an option can be obtained from the `type` property of the option:

```kt
option.type // The KType of the option. Captures the full type information of the option.
```

Options can be declared outside a patch and added to a patch manually:

```kt
val option = stringOption(name = "Option")

bytecodePatch(name = "Patch") {
    val value by option()
}
```

This is useful when the same option is referenced in multiple patches.

### 🧩 Extensions

An extension is a precompiled DEX file merged into the patched app before a patch is executed.
While patches are compile-time constructs, extensions are runtime constructs
that extend the patched app with additional classes.

Assume you want to add a complex feature to an app that would need multiple classes and methods:

```java
public class ComplexPatch {
    public static void doSomething() {
        // ...
    }
}
```

After compiling the above code as a DEX file, you can add the DEX file as a resource in the patches file
and use it in a patch:

```kt
val patch = bytecodePatch(name = "Complex patch") {
    extendWith("complex-patch.rve")

    execute {
        fingerprint.match().mutableMethod.addInstructions(
          0,
          "invoke-static { }, LComplexPatch;->doSomething()V"
        )
    }
}
```

ReVanced Patcher merges the classes from the extension into `context.classes` before executing the patch.
When the patch is executed, it can reference the classes and methods from the extension.

> [!NOTE]
>
> The [ReVanced Patches template](https://github.com/ReVanced/revanced-patches-template) repository
> is a template project to create patches and extensions.

> [!TIP]
> To see real-world examples of extensions,
> check out the repository for [ReVanced Patches](https://github.com/revanced/revanced-patches).

### ♻️ Finalization

Patches can have a finalization block called after all patches have been executed, in reverse order of patch execution.
The finalization block is called after all patches that depend on the patch have been executed.
This is useful for doing post-processing tasks.
A simple real-world example would be a patch that opens a resource file of the app for writing.
Other patches that depend on this patch can write to the file, and the finalization block can close the file.

```kt
val patch = bytecodePatch(name = "Patch") {
    dependsOn(
        bytecodePatch(name = "Dependency") {
            execute {
                print("1")
            }

            finalize {
                print("4")
            }
        }
    )

    execute {
        print("2")
    }

    finalize {
        print("3")
    }
}
```

Because `Patch` depends on `Dependency`, first `Dependency` is executed, then `Patch`.
Finalization blocks are called in reverse order of patch execution, which means,
first, the finalization block of `Patch`, then the finalization block of `Dependency` is called.
The output after executing the patch above would be `1234`.
The same order is followed for multiple patches depending on the patch.

## 💡 Additional tips

- When using `PatchLoader` to load patches, only patches with a name are loaded.
  Refer to the inline documentation of `PatchLoader` for detailed information.
- Patches can depend on others. Dependencies are executed first.
  The dependent patch will not be executed if a dependency raises an exception while executing.
- A patch can declare compatibility with specific packages and versions,
  but patches can still be executed on any package or version.
  It is recommended that compatibility is specified to present known compatible packages and versions.
    - If `compatibleWith` is not used, the patch is treated as compatible with any package
- If a package is specified with no versions, the patch is compatible with any version of the package
- If an empty array of versions is specified, the patch is not compatible with any version of the package.
  This is useful for declaring incompatibility with a specific package.
- A patch can raise a `PatchException` at any time of execution to indicate that the patch failed to execute.

## ⏭️ What's next

The next page explains the concept of fingerprinting in ReVanced Patcher.

Continue: [🔎 Fingerprinting](2_2_1_fingerprinting.md)
