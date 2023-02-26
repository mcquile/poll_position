USE PollPositionDB
GO

CREATE TABLE [dbo].[Token]
(
    [id] [int] IDENTITY(1,1) NOT NULL,
    [expired] [bit] NOT NULL,
    [revoked] [bit] NOT NULL,
    [token] [varchar](255) NULL,
    [tokenType] [varchar](255) NULL,
    [user_id] UNIQUEIDENTIFIER FOREIGN KEY REFERENCES [Users] NULL,
)
