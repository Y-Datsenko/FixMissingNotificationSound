# FixMissingNotificationSound

This is a simple Android project that we can use to reproduce one of the reasons of showing notification without custom sound after app update.

The project contains git history that can be used to track changes step by step. Each app state after every step was captured in the screenshot placed in the ../screenshot folder.
The repo was created as an test project for this article: https://yevhenii-datsenko.medium.com/a-reason-why-custom-notification-sound-might-not-work-on-android-4aea06b8c7c4

The repository contains simple Android project with one Fragment. This fragment has the following logic:
1. It creates a notification channel for Android devices with 8.0+ (API26+).
2. Shows notification channel id with channel sound URI to understand what URI the system should use to play the sound.
3. It displays ID of the sound file from the RAW resource folder.
4. It provides an ability to show notification by clicking on the button.
