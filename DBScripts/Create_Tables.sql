USE PollPositionDB;
GO

CREATE TABLE [Branches] (
  [BranchID] int IDENTITY(1,1) NOT NULL,
  [BranchName] varchar(250) NOT NULL,
  PRIMARY KEY ([BranchID])
);

CREATE TABLE [Sexes] (
  [SexID] bit NOT NULL,
  [Name] varchar(6) NOT NULL,
  PRIMARY KEY ([SexID])
);

CREATE TABLE [Users] (
  [UserId] UNIQUEIDENTIFIER NOT NULL,
  [FirstName] nvarchar(120) NULL,
  [LastName] nvarchar(120) NULL,
  [BranchID] int FOREIGN KEY REFERENCES Branches NULL,
  [DateOfBirth] date NULL,
  [ProfilePicLink] nvarchar(MAX) NULL,
  [Email] nvarchar(250) NOT NULL,
  [SexID] bit FOREIGN KEY REFERENCES Sexes NULL,
  [role] varchar(255) NULL,
  [password] varchar(255) NOT NULL,
  PRIMARY KEY ([UserID])
);

CREATE TABLE [Polls] (
  [PollID] UNIQUEIDENTIFIER NOT NULL,
  [PollCreator] UNIQUEIDENTIFIER FOREIGN KEY REFERENCES Users NOT NULL,
  [Title] nvarchar(250) NOT NULL,
  [Description] nvarchar(1000) NULL,
  [VoteStart] datetimeoffset NULL,
  [VoteEnd] datetimeoffset NULL,
  [NominationEndTime] datetimeoffset NULL,
  [PollCreationTime] datetimeoffset NOT NULL,
  PRIMARY KEY ([PollID])
);

CREATE TABLE [Nominations] (
  [NominationID] int IDENTITY(1,1) NOT NULL,
  [PollID] UNIQUEIDENTIFIER FOREIGN KEY REFERENCES Polls NOT NULL,
  [Nominee] nvarchar (1000) NOT NULL,
  [Nominator] UNIQUEIDENTIFIER FOREIGN KEY REFERENCES Users NOT NULL,
  PRIMARY KEY ([NominationID])
);

CREATE TABLE [UserRestrictions] (
  [UserRestrictionID] int IDENTITY(1,1) NOT NULL,
  [FirstNamePattern] nvarchar (250) NULL,
  [LastNamePattern] nvarchar (250) NULL,
  [SexRestrictedTo] bit FOREIGN KEY REFERENCES Sexes NULL,
  [BranchRestriction] int FOREIGN KEY REFERENCES Branches NULL,
  [DateOfBirthYounger] Date NULL,
  [DateOfBirthOlder] Date NULL,
  [PollID] UNIQUEIDENTIFIER FOREIGN KEY REFERENCES Polls NOT NULL,
  PRIMARY KEY ([UserRestrictionID])
);

CREATE TABLE [UserVotes] (
  [UserVote] int IDENTITY(1,1) NOT NULL,
  [UserID] UNIQUEIDENTIFIER NOT NULL,
  [NominationID] int FOREIGN KEY REFERENCES Nominations NOT NULL,
  PRIMARY KEY ([UserVote])
);

CREATE TABLE [SpecificUserRestrictions](
	[SpecificUserRestrictionID] int IDENTITY(1,1) NOT NULL,
	[PollID] UNIQUEIDENTIFIER FOREIGN KEY REFERENCES Polls NOT NULL,
	[UserID] UNIQUEIDENTIFIER FOREIGN KEY REFERENCES Users NOT NULL,
	[Restricted] bit NOT NULL
);