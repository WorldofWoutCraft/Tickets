CREATE TABLE comments (
  ticketid int NOT NULL,
  userid int NOT NULL,
  date datetime NOT NULL,
  comment varchar(255) NOT NULL,
  PRIMARY KEY (ticketid)
);

CREATE TABLE locations (
  locationid int NOT NULL,
  x float NOT NULL,
  y float NOT NULL,
  z float NOT NULL,
  yaw float NOT NULL,
  pitch float NOT NULL,
  world varchar(50) NOT NULL,
  PRIMARY KEY (locationid)
);

CREATE TABLE solvers (
  ticketid int NOT NULL,
  userid int NOT NULL
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
  PRIMARY KEY (ticketid)
);

CREATE TABLE users (
  userid int NOT NULL,
  uuid int NOT NULL,
  name int NOT NULL,
  PRIMARY KEY (userid)
);



ALTER TABLE comments ADD CONSTRAINT comments_ticket FOREIGN KEY REFERENCES tickets(ticketid) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE comments ADD CONSTRAINT comments_user FOREIGN KEY REFERENCES users (userid) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE solvers ADD CONSTRAINT ticket_user_ticket FOREIGN KEY REFERENCES tickets (ticketid) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE solvers ADD CONSTRAINT ticket_user_user FOREIGN KEY REFERENCES users (userid) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE tickets ADD CONSTRAINT ticketlocation FOREIGN KEY REFERENCES locations (locationid) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE tickets ADD CONSTRAINT ticketowner FOREIGN KEY REFERENCES users (userid) ON DELETE CASCADE ON UPDATE CASCADE;