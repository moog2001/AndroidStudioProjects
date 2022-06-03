

class Message{
  DateTime messageDate;
  String message;
  bool isMe;
  Message(this.messageDate, this.message,this.isMe);
}

class User{
  String userName;
  DateTime lastReadTime;
  List<Message> userMessages;
  String userImage;
  User(this.userName, this.lastReadTime, this.userMessages, this.userImage);
}