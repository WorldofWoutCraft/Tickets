CREATE TABLE comments (
  ticketid int NOT NULL,
  useruuid int NOT NULL,
  date datetime NOT NULL,
  comment varchar(255) NOT NULL,
  FOREIGN KEY(ticketid) REFERENCES tickets(ticketid),
  FOREIGN KEY(useruuid) REFERENCES users (uuid)
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
  useruuid int NOT NULL,
  FOREIGN KEY(ticketid) REFERENCES tickets (ticketid),
  FOREIGN KEY(useruuid) REFERENCES users (uuid)
);

CREATE TABLE tickets (
  ticketid int NOT NULL,
  ticketstatus varchar(50) NOT NULL,
  tickettype varchar(50) NOT NULL,
  description varchar(255) NOT NULL,
  owneruuid int NOT NULL,
  dateasked datetime NOT NULL,
  dateclosed datetime NOT NULL,
  priority int NOT NULL,
  locationid int NOT NULL,
  PRIMARY KEY(ticketid),
  FOREIGN KEY(locationid) REFERENCES locations (locationid),
  FOREIGN KEY(owneruuid) REFERENCES users(uuid)
);

CREATE TABLE users (
  uuid VARCHAR(100) NOT NULL,
  name int NOT NULL,
  PRIMARY KEY(uuid)
);