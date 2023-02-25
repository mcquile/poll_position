USE PollPositionDB
GO

CREATE TABLE [dbo].[Token]
(
    [id] [int] NOT NULL,
    [expired] [bit] NOT NULL,
    [revoked] [bit] NOT NULL,
    [token] [varchar](255) NULL,
    [tokenType] [varchar](255) NULL,
    [user_id] [binary](16) NULL,
    PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
    CONSTRAINT [UK_3wi2t4g8oiplxjflw3o2lkv2y] UNIQUE NONCLUSTERED 
(
	[token] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Token]  WITH CHECK ADD  CONSTRAINT [FKo460fqbjiyfkm6ubakeojaw4n] FOREIGN KEY([user_id])
REFERENCES [dbo].[Users] ([userId])
GO

ALTER TABLE [dbo].[Token] CHECK CONSTRAINT [FKo460fqbjiyfkm6ubakeojaw4n]
GO
