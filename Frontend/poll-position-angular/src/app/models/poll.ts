import {User} from "./user";

export interface Poll {
  id: string;
  creator: User;
  title: string;
  description: string;
  voteStart: Date;
  voteEnd: Date;
  nominationEndTime: Date;
  pollCreationTime: Date;
}
