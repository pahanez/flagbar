Flagbar
=======

Custom ProgressBar view.

Usage
=======

#####Add FlagBarLib to your project.
#####Add  xmlns:flagbar="http://schemas.android.com/apk/res-auto" to layout which contains Flagbar.

        <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:flagbar="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

#####Syntax:

        <com.pahanez.flagbar.Flagbar
        android:layout_width="100dp"
        android:layout_height="100dp"
        flagbar:stripesCount=["one","two","three","four"]
        flagbar:progress=["0...360"]
        flagbar:lineWidth=["integer"]
        flagbar:fps=["fps30","fps60"]
        flagbar:progressStart=["top","right","bottom","left"]
        flagbar:firstLineDirection=["clockwise" | "counterclockwise"]
        flagbar:secondLineDirection=["clockwise" | "counterclockwise"]
        flagbar:thirdLineDirection=["clockwise" | "counterclockwise"]
        flagbar:fourthLineDirection=["clockwise" | "counterclockwise"]
        flagbar:firstLineSpeed=["high","medium","low"]
        flagbar:secondLineSpeed=["high","medium","low"]
        flagbar:thirdLineSpeed=["high","medium","low"]
        flagbar:fourthLineSpeed=["high","medium","low"]
        flagbar:firstLineColor=["color"]
        flagbar:secondLineColor=["color"]
        flagbar:thirdLineColor=["color"]
        flagbar:fourthLineColor=["color"]
        flagbar:indeterminate=["true" | "false"] />
Templates
=======

| Country | Determinate | Indeterminate |
| :------------ |:---------------:|:-----:|
| Belarus      |<img src="https://raw.githubusercontent.com/pahanez/flagbar/master/img/belarus.png">|<img src="https://raw.githubusercontent.com/pahanez/flagbar/master/img/belarus_int.gif">|
| Ukraine      |<img src="https://raw.githubusercontent.com/pahanez/flagbar/master/img/ukraine.png">|<img src="https://raw.githubusercontent.com/pahanez/flagbar/master/img/ukraine_int.gif">|
| Poland |<img src="https://raw.githubusercontent.com/pahanez/flagbar/master/img/poland.png">|<img src="https://raw.githubusercontent.com/pahanez/flagbar/master/img/poland_int.gif">|
| Germany |<img src="https://raw.githubusercontent.com/pahanez/flagbar/master/img/germany.png">|<img src="https://raw.githubusercontent.com/pahanez/flagbar/master/img/germany_int.gif">|





