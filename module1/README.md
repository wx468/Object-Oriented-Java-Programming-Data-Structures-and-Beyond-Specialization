## How To Setup in IntelliJ?
### 1. Import Project 
* "UCSDUnfoldingMaps"
* Open "src", Module 1 "HelloWorld"
* Build
* How to Run: 
  * Click Run in Menu
  * Edit Configurations
  * Click "+" in the ledt panel
  * Choose "Applet"
  * fill in name you want to use, for example "runhelloworld", choose the applet class you want to run with, Apply and OK
  * Click Run in the menu click run and choose "runhelloworld".
#### If the following shows in the window means you are not using the correct JDK, JAVA VERSION
Error: Could not find or load main class sun.applet.AppletViewer<br/>

Caused by: java.lang.ClassNotFoundException: sun.applet.AppletViewer

#### Please download Java 7/ Java 8 from oracle: [HERE!](https://www.oracle.com/technetwork/java/javase/downloads/java-archive-javase8-2177648.html)

### 2. Setup JDK
* Back to IntelliJ your project interface
* Click File in Menu
* Choose "Project Structure"
* Click "Project" in the left panel
* In the right side "SDK" choose NEW
* Import the "jdk1.8....." or "jdk1.7...." you just download.
* the file is usually under the "Library/Java/JavaVirtualMachines" path
* Click "Apply" and "OK"

#### Then, chick run in the menu and click "runhelloworld"
#### It finally works!

### 3. After run several times, the map not shown and the following in the window:
java.io.IOException: Server returned HTTP response code: 403 for URL: http://mt1.google.com/vt/v=w2p.116&hl=de&x=1&y=1&z=1&s=Galileo<br/>
<br/> which means google think you are a bot. Try to run the day after
#### The best Way:
##### Change the Code in the "HelloWorld" file :
#####   AbstractMapProvider provider = new Google.GoogleTerrainProvider();
#####   to
#####   AbstractMapProvider provider = new Microsoft.HybridProvider();

If that won't work after several times run, Choose any providers: [HERE!](http://unfoldingmaps.org/javadoc/de/fhpotsdam/unfolding/providers/package-summary.html)

### Hope this may help you, I struggle and search online almost 5 hours to setup everything ^^ Enjoy!
