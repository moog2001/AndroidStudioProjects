import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

void main() {
  runApp(
    /// Providers are above [MyApp] instead of inside it, so that tests
    /// can use [MyApp] while mocking the providers
    ChangeNotifierProvider(
      create: (_) => SignInDetailsModel(),
      child: MyApp(),
    ),
  );
}

class MyApp extends StatelessWidget {
  static const String _title = 'Provider Sign-In Example';

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: _title, home: HomePage(), debugShowCheckedModeBanner: false);
  }
}

class HomePage extends StatelessWidget {
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
            title: const Text('Provider Login Example'),
            actions: <Widget>[
              IconButton(
                icon: const Icon(Icons.person),
                onPressed: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => LoginRoute()),
                  );
                },
              ),
            ]),
        body: Center(child: Consumer<SignInDetailsModel>(
          builder: (context, userModel, child) {
            String message = (userModel.user == ""
                ? "Please sign-in"
                : "Welcome ${userModel.user}");
            return Text(message, style: Theme.of(context).textTheme.headline4);
          },
        )));
  }
}

class LoginRoute extends StatelessWidget {
  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  final userNameTextController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    final userField = TextField(
      style: style,
      controller: userNameTextController,
      decoration: InputDecoration(
          contentPadding: EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 15.0),
          hintText: "User Name",
          border: OutlineInputBorder(borderRadius: BorderRadius.horizontal())),
    );
    final passwordField = TextField(
      obscureText: true,
      style: style,
      decoration: const InputDecoration(
          contentPadding: EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 15.0),
          hintText: "Password",
          border: OutlineInputBorder(borderRadius: BorderRadius.horizontal())),
    );
    final loginButton = Material(
      elevation: 5.0,
      color: Colors.blueAccent[400],
      child: MaterialButton(
        minWidth: MediaQuery.of(context).size.width,
        padding: EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 15.0),
        onPressed: () {
          Provider.of<SignInDetailsModel>(context, listen: false)
              .signIn(userNameTextController.text);

          // Navigate back to first route when tapped.
          Navigator.pop(context);
        },
        child: Text("Sign-In",
            textAlign: TextAlign.center,
            style: style.copyWith(
                color: Colors.white, fontWeight: FontWeight.bold)),
      ),
    );

    return Scaffold(
      body:
    );
  }
}

class SignInDetailsModel with ChangeNotifier {
  String _user = "";
  DateTime? _signInOn;
  String get user => _user;
  DateTime get signInOn => _signInOn!;

  void signIn(String userName) {
    _user = userName;
    _signInOn = DateTime.now();
    notifyListeners();
  }
}
