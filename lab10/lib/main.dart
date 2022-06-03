import 'dart:async';
import 'dart:convert';
import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'globals.dart' as globals;
import 'package:http/http.dart' as http;
import 'package:random_date/random_date.dart';
import 'package:intl/intl.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:cloud_firestore/cloud_firestore.dart';

import 'dart:developer' as developer;

late DatabaseReference ref;

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  final Future<FirebaseApp> _fbApp = Firebase.initializeApp();

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
        home: FutureBuilder(
            future: _fbApp,
            builder: (context, snapshot) {
              if (snapshot.hasError) {
                print("error " + snapshot.error.toString());
                return Text("error");
              } else if (snapshot.hasData) {
                return MyHomePage(title: 'Flutter Demo Home Page');
              } else {
                return Center(
                  child: CircularProgressIndicator(),
                );
              }
            }));
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

class Item {
  final String name;
  final double price;
  final String date;
  final Icon icon;

  Item(
      {this.name = "",
      this.price = 0,
      this.date = "",
      this.icon = const Icon(
        Icons.add,
        size: 35,
        color: Colors.white,
      )});
}

DateTime getRandomDate() {
  var dateTime =
      RandomDate.withRange(DateTime.now().year - 1, DateTime.now().year + 1);
  return dateTime.random();
}

Random random = new Random();

class GameItem {
  final int itemId;
  final String name;
  final double price;
  final String imageLink;
  late bool isPositive;

  GameItem(
      {required this.itemId,
      required this.name,
      required this.price,
      required this.imageLink}) {
    isPositive = random.nextBool();
  }

  factory GameItem.fromJson(Map<String, dynamic> json) {
    return GameItem(
        itemId: int.parse(json['gameID']),
        name: json['title'] as String,
        price: double.parse(json['normalPrice']),
        imageLink: json['thumb'] as String);
  }
}

class _MyHomePageState extends State<MyHomePage> {
  int _selectedIndex = 0;
  late List<Widget> _widgetOptions;
  static const TextStyle optionStyle =
      TextStyle(fontSize: 30, fontWeight: FontWeight.bold);

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  double sumState = -247;
  late double money = 0;

  late DatabaseReference _testRef;

  @override
  void initState() {
    _testRef = FirebaseDatabase.instance.ref();
    updateMoney();
  }

  updateMoney() async {
    final snapshot = await _testRef.child('money').get();
    if (snapshot.exists) {
      var testDouble = snapshot.child("money").value;
      setState(() {
        money = testDouble as double;
      });
    } else {
      print('No data available.');
    }
  }

  Widget _tabSection(BuildContext context) {
    paymentButtonPressed(double amount) {
      setState(() {
        money = money + amount;
      });
      _testRef.update({"money": money});
    }

    bool fetched = false;
    Future<List<GameItem>> fetchGameItems() async {
      final response =
          await http.get(Uri.parse('https://www.cheapshark.com/api/1.0/deals'));

      if (response.statusCode == 200) {
        // If the server did return a 200 OK response,
        // then parse the JSON.
        Iterable l = json.decode(response.body);
        List<GameItem> gameItems =
            List<GameItem>.from(l.map((model) => GameItem.fromJson(model)));
        double sum = 0;
        if (!fetched) {
          for (var value in gameItems) {
            double temp;
            if (value.isPositive) {
              temp = value.price;
            } else {
              temp = -value.price;
            }
            sum += temp;
          }
          fetched = true;
          // setState(() {
          //   sumState = sum;
          // });
        }

        return gameItems;
      } else {
        // If the server did not return a 200 OK response,
        // then throw an exception.
        throw Exception('Failed to load album');
      }
    }

    Future<List<GameItem>> gameItemsFuture;
    gameItemsFuture = fetchGameItems();
    final DateFormat formatter = DateFormat('yyyy-MM-dd');

    return DefaultTabController(
      length: 2,
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          Container(
            padding: EdgeInsets.all(3),
            decoration: BoxDecoration(
                color: globals.blankColor,
                borderRadius: BorderRadius.all(Radius.circular(50))),
            child: const TabBar(
                labelColor: Colors.black54,
                indicator: BoxDecoration(
                  borderRadius: BorderRadius.all(Radius.circular(50)),
                  color: Colors.white,
                ),
                tabs: [
                  Tab(text: "Transactions"),
                  Tab(text: "Upcoming Bills"),
                ]),
          ),
          Container(
              color: Colors.white,
              height: 280,
              child: FutureBuilder<List<GameItem>>(
                builder: (BuildContext context,
                    AsyncSnapshot<List<GameItem>> snapshot) {
                  List<GameItem>? gameItemList = snapshot.data;
                  if (gameItemList == null) {
                    return Text("null");
                  }

                  double sum = 0;
                  for (var value in gameItemList) {
                    double temp;
                    if (value.isPositive) {
                      temp = value.price;
                    } else {
                      temp = -value.price;
                    }
                    sum += temp;
                  }

                  return TabBarView(children: [
                    SizedBox(
                        height: 280,
                        child: ListView.separated(
                            scrollDirection: Axis.vertical,
                            shrinkWrap: true,
                            itemBuilder: (BuildContext context, int index) {
                              GameItem currentItem = gameItemList[index];
                              DateTime dateTime = getRandomDate();
                              return Row(
                                  mainAxisAlignment: MainAxisAlignment.start,
                                  // crossAxisAlignment: CrossAxisAlignment.stretch,
                                  children: [
                                    Container(
                                      width: 50,
                                      height: 50,
                                      child:
                                          Image.network(currentItem.imageLink),
                                      decoration: BoxDecoration(
                                        color: globals.blankColor,
                                        borderRadius: BorderRadius.circular(10),
                                      ),
                                    ),
                                    Column(
                                      // mainAxisSize: MainAxisSize.min,
                                      children: [
                                        Container(
                                          child: Text(
                                            currentItem.name,
                                            style:
                                                const TextStyle(fontSize: 20),
                                          ),
                                          width: 220,
                                        ),
                                        Text(formatter.format(dateTime)),
                                      ],
                                    ),
                                    currentItem.isPositive
                                        ? const Text(
                                            "+ ",
                                            style:
                                                TextStyle(color: Colors.green),
                                          )
                                        : const Text("- ",
                                            style:
                                                TextStyle(color: Colors.red)),
                                    Text(
                                      "\$ " + currentItem.price.toString(),
                                      textAlign: TextAlign.right,
                                      style: currentItem.isPositive
                                          ? const TextStyle(
                                              color: Colors.green, fontSize: 20)
                                          : const TextStyle(
                                              color: Colors.red, fontSize: 20),
                                    )
                                  ]);
                            },
                            separatorBuilder:
                                (BuildContext context, int index) =>
                                    const Divider(),
                            itemCount: gameItemList.length)),
                    Container(
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: [
                          Text("Game Business Total: "),
                          Text(sum.toStringAsFixed(2)),
                          TextButton(
                              onPressed: () => paymentButtonPressed(sum),
                              child: Container(
                                child: Text(
                                  "Agree transitions",
                                  style: TextStyle(color: Colors.black54),
                                ),
                                decoration: BoxDecoration(
                                    color: Colors.white,
                                    borderRadius:
                                        BorderRadius.all(Radius.circular(10))),
                                padding: EdgeInsets.all(10),
                              )),
                        ],
                      ),
                      decoration: BoxDecoration(
                          color: globals.blankColor,
                          borderRadius: BorderRadius.all(Radius.circular(50))),
                      margin: EdgeInsets.all(10),
                    )
                  ]);
                },
                future: gameItemsFuture,
              )),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    _widgetOptions = <Widget>[
      const Text(
        'Index 0: Home',
        style: optionStyle,
      ),
      const Text(
        'Index 1: Home',
        style: optionStyle,
      ),
      Stack(
        children: [
          Container(color: globals.subColor),
          Container(
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.height,
            margin: const EdgeInsets.only(top: 100),
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
                FutureBuilder(
                  future: Firebase.initializeApp(),
                  builder: (context, snapshot) {
                    if (!snapshot.hasData) {
                      return Center(
                        child: CircularProgressIndicator(),
                      );
                    }
                    return Text(
                      "\$ " + money.toStringAsFixed(2),
                      style: TextStyle(
                          fontFamily: 'RobotoMono',
                          color: Colors.black,
                          fontSize: 25,
                          fontWeight: FontWeight.bold),
                    );
                  },
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
                        color: globals.subColor,
                      ),
                      decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(100),
                          border:
                              Border.all(width: 2, color: globals.subColor)),
                    ),
                    const SizedBox(width: 20),
                    Container(
                      width: 50,
                      height: 50,
                      child: Icon(
                        Icons.qr_code,
                        size: 35,
                        color: globals.subColor,
                      ),
                      decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(100),
                          border:
                              Border.all(width: 2, color: globals.subColor)),
                    ),
                    const SizedBox(width: 20),
                    Container(
                      width: 50,
                      height: 50,
                      child: Icon(
                        Icons.send,
                        size: 35,
                        color: globals.subColor,
                      ),
                      decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(100),
                          border:
                              Border.all(width: 2, color: globals.subColor)),
                    ),
                  ],
                ),
                const SizedBox(height: 20),
                Container(
                  margin: const EdgeInsets.symmetric(horizontal: 20),
                  child: Column(
                    children: <Widget>[
                      _tabSection(context),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
      Container(child: Text("Item 3"))
    ];
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      body: _widgetOptions.elementAt(_selectedIndex),
      bottomNavigationBar: BottomNavigationBar(
        items: const <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: '',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.bar_chart_rounded),
            label: '',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.account_balance_wallet_outlined),
            label: '',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.account_circle_outlined),
            label: '',
          ),
        ],
        currentIndex: _selectedIndex,
        selectedItemColor: globals.subColor,
        onTap: _onItemTapped,
        backgroundColor: globals.blankColor,
        unselectedItemColor: Colors.grey,
      ),
    );
  }
}
