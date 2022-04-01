# 2.1

## What changes are made when you add a second Activity to your app by choosing File > New > Activity and an Activity template? Choose one:

### The second Activity is added as a Java class, the XML layout file is created, and the AndroidManifest.xml file is changed to declare a second Activity.

## What happens if you remove the android:parentActivityName and the <meta-data> elements from the second Activity declaration in the AndroidManifest.xml file? Choose one:

### The Back button no longer works in the second Activity to send the user back to the main Activity.

## Which constructor method do you use to create a new explicit Intent? Choose one:

### new Intent(Context context, Class<?> class)

## In the HelloToast app homework, how do you add the current value of the count to the Intent? Choose one:

### As an Intent extra

## In the HelloToast app homework, how do you display the current count in the second "Hello" Activity? Choose one:

### All of the above.


# 2.2


## If you run the homework app before implementing onSaveInstanceState(), what happens if you rotate the device? Choose one:
	
### The counter is reset to 0, but the contents of the EditText is preserved.

## What Activity lifecycle methods are called when a device-configuration change (such as rotation) occurs? Choose one:

### Android shuts down your Activity by calling onPause(), onStop(), and onDestroy(), and then starts it over again, calling onCreate(), onStart(), and onResume().

## When in the Activity lifecycle is onSaveInstanceState() called? Choose one:

### onSaveInstanceState() is called before the onDestroy() method.

## Which Activity lifecycle methods are best to use for saving data before the Activity is finished or destroyed? Choose one:

### onDestroy()


# 2.3


## Which constructor method do you use to create an implicit Intent to launch a camera app?

### new Intent(String action)

## When you create an implicit Intent object, which of the following is true?

### All of the above.

## Which Intent action do you use to take a picture with a camera app?

### Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


	


