import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class RowElementWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
        child: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [Text("test"), Text("testes")]));
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final Color subColor = const Color.fromRGBO(55, 137, 131, 1);

  int buttonSelected = 0;

  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      body: Stack(
        children: [
          Container(color: subColor),
          Container(
            width: double.infinity,
            height: double.infinity,
            margin: const EdgeInsets.only(top: 150),
            decoration: const BoxDecoration(
              color: Colors.white,
              borderRadius: BorderRadius.only(
                  topLeft: Radius.circular(25), topRight: Radius.circular(25)),
            ),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                const SizedBox(height: 30),
                const Text(
                  'Total Balance',
                  style: TextStyle(
                      fontFamily: 'RobotoMono',
                      color: Colors.grey,
                      fontSize: 18),
                ),
                const SizedBox(height: 10),
                const Text(
                  '\$ 2,548.00',
                  style: TextStyle(
                      fontFamily: 'RobotoMono',
                      color: Colors.black,
                      fontSize: 25,
                      fontWeight: FontWeight.bold),
                ),
                const SizedBox(height: 30),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Container(
                      width: 50,
                      height: 50,
                      child: Icon(
                        Icons.add,
                        size: 35,
                        color: subColor,
                      ),
                      decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(100),
                          border: Border.all(width: 2, color: subColor)),
                    ),
                    const SizedBox(width: 20),
                    Container(
                      width: 50,
                      height: 50,
                      child: Icon(
                        Icons.qr_code,
                        size: 35,
                        color: subColor,
                      ),
                      decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(100),
                          border: Border.all(width: 2, color: subColor)),
                    ),
                    const SizedBox(width: 20),
                    Container(
                      width: 50,
                      height: 50,
                      child: Icon(
                        Icons.send,
                        size: 35,
                        color: subColor,
                      ),
                      decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(100),
                          border: Border.all(width: 2, color: subColor)),
                    ),
                  ],
                ),
                const SizedBox(height: 50),
                Container(
                  margin: const EdgeInsets.symmetric(horizontal: 50),
                  decoration: BoxDecoration(
                    color: const Color.fromRGBO(244, 246, 246, 1),
                    borderRadius: BorderRadius.circular(15),
                  ),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: [
                      TextButton(
                          onPressed: () {
                            setState(() {
                              buttonSelected = 0;
                            });
                          },
                          style: buttonSelected == 0
                              ? ButtonStyle(
                                  backgroundColor: MaterialStateProperty.all<Color>(
                                      Colors.white),
                                  padding: MaterialStateProperty.all(
                                      const EdgeInsets.symmetric(
                                          horizontal: 25)),
                                  shape: MaterialStateProperty.all(
                                      RoundedRectangleBorder(
                                          borderRadius:
                                              BorderRadius.circular(10))))
                              : ButtonStyle(
                                  backgroundColor: MaterialStateProperty.all<Color>(
                                      const Color.fromRGBO(244, 246, 246, 1)),
                                  padding: MaterialStateProperty.all(
                                      const EdgeInsets.symmetric(horizontal: 25)),
                                  shape: MaterialStateProperty.all(RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)))),
                          child: const Text('Transactions', style: TextStyle(color: Colors.black))),
                      TextButton(
                          onPressed: () {
                            setState(() {
                              buttonSelected = 1;
                            });
                          },
                          style: buttonSelected == 1
                              ? ButtonStyle(
                                  backgroundColor: MaterialStateProperty.all<Color>(
                                      Colors.white),
                                  padding: MaterialStateProperty.all(
                                      const EdgeInsets.symmetric(
                                          horizontal: 25)),
                                  shape: MaterialStateProperty.all(
                                      RoundedRectangleBorder(
                                          borderRadius:
                                              BorderRadius.circular(10))))
                              : ButtonStyle(
                                  backgroundColor: MaterialStateProperty.all<Color>(
                                      const Color.fromRGBO(244, 246, 246, 1)),
                                  padding: MaterialStateProperty.all(
                                      const EdgeInsets.symmetric(horizontal: 25)),
                                  shape: MaterialStateProperty.all(RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)))),
                          child: const Text('Upcoming Bills', style: TextStyle(color: Colors.black)))
                    ],
                  ),
                ),
                Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [RowElementWidget(), RowElementWidget()],
                )
              ],
            ),
          ),
        ],
      ),
    );
  }
}
