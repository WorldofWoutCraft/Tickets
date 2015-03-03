CREATE TABLE comments (
  ticketid int NOT NULL,
  userid int NOT NULL,
  date datetime NOT NULL,
  comment varchar(255) NOT NULL,
  FOREIGN KEY(ticketid) REFERENCES tickets(ticketid),
  FOREIGN KEY(userid) REFERENCES users (userid)
);

CREATE TABLE locations (
  locationid int NOT NULL,
  x float NOT NULL,
  y float NOT NULL,
  z float NOT NULL,
  yaw float NOT NULL,
  pitch float NOT NULL,
  world varchar(50) NOT NULL,
  PRIMARY KEY(locationid)
);

CREATE TABLE solvers (
  ticketid int NOT NULL,
  userid int NOT NULL,
  FOREIGN KEY(ticketid) REFERENCES tickets (ticketid),
  FOREIGN KEY(userid) REFERENCES users (userid)
);

CREATE TABLE tickets (
  ticketid int NOT NULL,
  ticketstatus varchar(50) NOT NULL,
  tickettype varchar(50) NOT NULL,
  description varchar(255) NOT NULL,
  ownerid int NOT NULL,
  dateasked datetime NOT NULL,
  dateclosed datetime NOT NULL,
  priority int NOT NULL,
  locationid int NOT NULL,
  PRIMARY KEY(ticketid),
  FOREIGN KEY(locationid) REFERENCES locations (locationid),
  FOREIGN KEY(ownerid) REFERENCES users(userid)
);

CREATE TABLE users (
  userid int NOT NULL,
  uuid int NOT NULL,
  name int NOT NULL,
  PRIMARY KEY(userid)
);