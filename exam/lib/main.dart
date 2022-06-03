import 'dart:convert';
import 'dart:io';

import 'package:exam/utility.dart';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:firebase_core/firebase_core.dart';
import "globals.dart" as globals;
import 'dart:developer' as developer;
import 'package:intl/intl.dart';

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
        initialRoute: '/',
        routes: {
          '/': (context) => FutureBuilder(
              future: Firebase.initializeApp(),
              builder: (context, snapshot) {
                if (snapshot.hasError) {
                  developer.log("log",
                      name: 'firebase', error: snapshot.error.toString());
                }
                if (snapshot.hasData) {
                  return FirstPage();
                } else {
                  return Center(child: CircularProgressIndicator());
                }
              }),
          '/second': (context) => SecondPage(),
        });
  }
}

class FirstPage extends StatefulWidget {
  const FirstPage({Key? key}) : super(key: key);

  @override
  State<FirstPage> createState() => _FirstPageState();
}

class _FirstPageState extends State<FirstPage> {
  var users = <User>[];

  // late DatabaseReference ref;
  static const TextStyle optionStyle =
      TextStyle(fontSize: 30, fontWeight: FontWeight.bold);
  late List<Widget> _widgetOptions;
  int _selectedIndex = 0;

  late User theUser;

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  void initState() {
    // ref = FirebaseDatabase.instance.ref("data/users").ref;
    // ref.onChildAdded.listen((event) {
    //   var messages = <Message>[];
    //   var user = User(event.snapshot.child("userName").value.toString(), DateTime.parse(event.snapshot.child("lastReadTime").value.toString()), messages, event.snapshot.child("userImage").value.toString());
    //
    //   // String userName;
    //   // DateTime lastReadTime;
    //   // List<Message> userMessages;
    //   // String userImage;
    //
    //   String userName = event.snapshot.child("this").value.toString();
    //   var refUser = FirebaseDatabase.instance.ref("data/users/$userName/userMessages");
    //
    //   refUser.onChildAdded.listen((event) {
    //     for (final child in event.snapshot.children) {
    //       print(child.ref.);
    //     }
    //   });
    //   // print("ref: "+ userMessageRef.toString());
    //   // print("value: "+ event.snapshot.child("userMessages").value.toString());
    //   // userMessageRef.onChildAdded.listen((event) {
    //   //   for (final child in event.snapshot.children) {
    //   //     print("Test loop: " + child.value.toString());
    //   //   }
    //   // });
    // });
    var messages = <Message>[];
    Message message1 = new Message(DateTime.now(), "testMessage", false);
    Message message2 = new Message(DateTime.now(), "testMessage2", true);

    messages.add(message1);
    messages.add(message2);
    var testUser = User("test", DateTime.now(), messages,
        "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png");
    users.add(testUser);
    users.add(testUser);
  }

  // BoxDecoration radius = BoxDecoration()

  @override
  Widget build(BuildContext context) {
    _widgetOptions = <Widget>[
      const Text(
        'Index 0: Home',
        style: optionStyle,
      ),
      Container(
        height: MediaQuery.of(context).size.height,
        width: MediaQuery.of(context).size.width,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          mainAxisAlignment: MainAxisAlignment.start,
          children: [
            Text(
              "Chat",
              style: optionStyle,
            ),
            SizedBox(
              width: 200,
              child: TextField(
                decoration: InputDecoration(
                  border: InputBorder.none,
                  labelText: 'Search Here',
                  hintText: 'Enter Your Name',
                ),
              ),
            ),
            Container(
                height: 70,
                child: ListView.builder(
                  scrollDirection: Axis.horizontal,
                  shrinkWrap: true,
                  itemBuilder: (BuildContext context, int index) {
                    User currentUser = users[index];
                    return Row(
                      children: [
                        Container(
                          width: 40,
                          height: 40,
                          child: ClipRRect(
                            borderRadius: BorderRadius.circular(20),
                            child: Image(
                              image: NetworkImage(currentUser.userImage),
                            ),
                          ),
                          decoration: BoxDecoration(
                              borderRadius:
                                  BorderRadius.all(Radius.circular(50))),
                        ),
                        SizedBox(
                          width: 20,
                        ),
                      ],
                    );
                  },
                  itemCount: users.length,
                )),
            Expanded(
                child: ListView.builder(
              scrollDirection: Axis.vertical,
              shrinkWrap: true,
              itemBuilder: (BuildContext context, int index) {
                var currentUser = users[index];
                return Column(
                  children: [
                    Row(children: [
                      ElevatedButton(
                        onPressed: () {
                          theUser = users[index];
                          Navigator.pushNamed(context, '/second',
                              arguments: theUser);
                        },
                        child: Container(
                          width: MediaQuery.of(context).size.width - 50,
                          height: 70,
                          child: Row(
                            children: [
                              ClipRRect(
                                borderRadius: BorderRadius.circular(50),
                                child: Image(
                                  image: NetworkImage(currentUser.userImage),
                                ),
                              ),
                              SizedBox(width: 20),
                              Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text(
                                    currentUser.userName,
                                    style: TextStyle(fontSize: 20),
                                  ),
                                  Text(currentUser.userMessages.last.message),
                                ],
                              ),
                              SizedBox(
                                width: 100,
                              ),
                              Text(DateFormat('yyyy-MM-dd')
                                  .format(currentUser.lastReadTime)),
                            ],
                          ),
                        ),
                      ),
                    ]),
                    SizedBox(
                      height: 20,
                    )
                  ],
                );
              },
              itemCount: users.length,
            ))
          ],
        ),
      ),
      const Text(
        'Index 2: Home',
        style: optionStyle,
      ),
      Container(child: Text("Item 3"))
    ];

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
        backgroundColor: Colors.white,
        unselectedItemColor: Colors.grey,
      ),
    );
  }
}

// class SecondPage extends StatelessWidget {
//   const SecondPage({Key? key}) : super(key: key);
//
//   @override
//   Widget build(BuildContext context) {
//     final user = ModalRoute.of(context)!.settings.arguments as User;
//     return Scaffold(
//       backgroundColor: Colors.white,
//       body: Container(
//           height: MediaQuery.of(context).size.height,
//           width: MediaQuery.of(context).size.width,
//           margin: EdgeInsets.only(top: 30),
//           child: Column(
//             crossAxisAlignment: CrossAxisAlignment.start,
//             children: [
//               Container(
//                 height: 100,
//                 child: Row(
//                   mainAxisAlignment: MainAxisAlignment.start,
//                   crossAxisAlignment: CrossAxisAlignment.center,
//                   children: [
//                     SizedBox(width: 30),
//                     Container(
//                       height: 90,
//                       width: 90,
//                       child: ClipRRect(
//                         borderRadius: BorderRadius.circular(100),
//                         child: Image(
//                           image: NetworkImage(user.userImage),
//                         ),
//                       ),
//                     ),
//                     Column(
//                       mainAxisAlignment: MainAxisAlignment.center,
//                       crossAxisAlignment: CrossAxisAlignment.center,
//                       children: [
//                         Text(
//                           user.userName,
//                           style: TextStyle(fontSize: 20),
//                         ),
//                         Text("Active Now"),
//                       ],
//                     ),
//                     SizedBox(
//                       width: 100,
//                     ),
//                     Icon(
//                       Icons.call,
//                       size: 30,
//                       color: globals.subColor,
//                     ),
//                     SizedBox(
//                       width: 30,
//                     ),
//                     Icon(
//                       Icons.camera_alt,
//                       size: 30,
//                       color: globals.subColor,
//                     ),
//                   ],
//                 ),
//               ),
//               Divider(
//                 color: Colors.black54,
//               ),
//               Expanded(
//                 child: ListView.builder(
//                     scrollDirection: Axis.vertical,
//                     shrinkWrap: true,
//                     itemCount: user.userMessages.length,
//                     itemBuilder: (context, index) {
//                       final messages = user.userMessages;
//                       return Column(
//                         mainAxisAlignment: MainAxisAlignment.center,
//                         crossAxisAlignment: CrossAxisAlignment.start,
//                         children: [
//                           messages[index].isMe
//                               ? Row(
//                                   children: [
//                                     Container(
//                                       height: 70,
//                                       width: 70,
//                                       child: ClipRRect(
//                                         borderRadius: BorderRadius.circular(50),
//                                         child: Image(
//                                           image: NetworkImage(user.userImage),
//                                         ),
//                                       ),
//                                     ),
//                                     SizedBox(
//                                       width: 10,
//                                     ),
//                                     Container(
//                                       child: Text(
//                                         messages[index].message,
//                                         style: TextStyle(
//                                             fontSize: 15, color: Colors.black),
//                                       ),
//                                       decoration: BoxDecoration(
//                                         borderRadius: BorderRadius.all(
//                                             Radius.circular(10)),
//                                         color: globals.blankColor,
//                                       ),
//                                       width: 200,
//                                       padding: EdgeInsets.all(20),
//                                       alignment: Alignment.centerLeft,
//                                     )
//                                   ],
//                                 )
//                               : Row(
//                                   children: [
//                                     SizedBox(
//                                       width: 200,
//                                     ),
//                                     Container(
//                                       child: Text(
//                                         messages[index].message,
//                                         textAlign: TextAlign.right,
//                                       ),
//                                       decoration: BoxDecoration(
//                                         borderRadius: BorderRadius.all(
//                                             Radius.circular(10)),
//                                         color: globals.subColorSoft,
//                                       ),
//                                       width: 200,
//                                       padding: EdgeInsets.all(20),
//                                       alignment: Alignment.centerLeft,
//                                     ),
//                                   ],
//                                 )
//                         ],
//                       );
//                     }),
//               )
//             ],
//           )),
//       bottomSheet: Container(
//         padding: EdgeInsets.all(10),
//         child: TextField(
//           decoration: InputDecoration(
//             contentPadding: EdgeInsets.all(10),
//             border: InputBorder.none,
//             hintText: 'Send Message',
//           ),
//           onSubmitted: (){
//            
//           },
//         ),
//       ),
//     );
//   }
// }

class SecondPage extends StatefulWidget {
  const SecondPage({Key? key}) : super(key: key);

  @override
  State<SecondPage> createState() => _SecondPageState();
}

class _SecondPageState extends State<SecondPage> {
  
  var user;
  var userMessages = <Message>[];
  
  @override
  Widget build(BuildContext context) {
    user = ModalRoute.of(context)!.settings.arguments as User;
    return Scaffold(
      backgroundColor: Colors.white,
      body: Container(
          height: MediaQuery.of(context).size.height,
          width: MediaQuery.of(context).size.width,
          margin: EdgeInsets.only(top: 30),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Container(
                height: 100,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.start,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    SizedBox(width: 30),
                    Container(
                      height: 90,
                      width: 90,
                      child: ClipRRect(
                        borderRadius: BorderRadius.circular(100),
                        child: Image(
                          image: NetworkImage(user.userImage),
                        ),
                      ),
                    ),
                    Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        Text(
                          user.userName,
                          style: TextStyle(fontSize: 20),
                        ),
                        Text("Active Now"),
                      ],
                    ),
                    SizedBox(
                      width: 100,
                    ),
                    Icon(
                      Icons.call,
                      size: 30,
                      color: globals.subColor,
                    ),
                    SizedBox(
                      width: 30,
                    ),
                    Icon(
                      Icons.camera_alt,
                      size: 30,
                      color: globals.subColor,
                    ),
                  ],
                ),
              ),
              Divider(
                color: Colors.black54,
              ),
              Expanded(
                child: ListView.builder(
                    scrollDirection: Axis.vertical,
                    shrinkWrap: true,
                    itemCount: user.userMessages.length,
                    itemBuilder: (context, index) {
                      userMessages = user.userMessages;
                      return Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          userMessages[index].isMe
                              ? Row(
                            children: [
                              Container(
                                height: 70,
                                width: 70,
                                child: ClipRRect(
                                  borderRadius: BorderRadius.circular(50),
                                  child: Image(
                                    image: NetworkImage(user.userImage),
                                  ),
                                ),
                              ),
                              SizedBox(
                                width: 10,
                              ),
                              Container(
                                child: Text(
                                  userMessages[index].message,
                                  style: TextStyle(
                                      fontSize: 15, color: Colors.black),
                                ),
                                decoration: BoxDecoration(
                                  borderRadius: BorderRadius.all(
                                      Radius.circular(10)),
                                  color: globals.blankColor,
                                ),
                                width: 200,
                                padding: EdgeInsets.all(20),
                                alignment: Alignment.centerLeft,
                              )
                            ],
                          )
                              : Row(
                            children: [
                              SizedBox(
                                width: 200,
                              ),
                              Container(
                                child: Text(
                                  userMessages[index].message,
                                  textAlign: TextAlign.right,
                                ),
                                decoration: BoxDecoration(
                                  borderRadius: BorderRadius.all(
                                      Radius.circular(10)),
                                  color: globals.subColorSoft,
                                ),
                                width: 200,
                                padding: EdgeInsets.all(20),
                                alignment: Alignment.centerLeft,
                              ),
                            ],
                          )
                        ],
                      );
                    }),
              )
            ],
          )),
      bottomSheet: Container(
        padding: EdgeInsets.all(10),
        child: TextField(
          decoration: InputDecoration(
            contentPadding: EdgeInsets.all(10),
            border: InputBorder.none,
            hintText: 'Send Message',
          ),
          onSubmitted: (context){
            setState(() {
              userMessages.add(value)
            });
          },
        ),
      ),
    );
  }
}

