import {User} from "./user";

export interface Poll{
  pollID: string;
  pollCreator: User;
  title: string;
  description: string;
  voteStart: Date;
  voteEnd: Date;
  nominationEndTime: Date;
  pollCreationTime: Date;
}
