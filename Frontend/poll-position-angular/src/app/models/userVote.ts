import {User} from "./user";
import {Nomination} from "./nomination";

export interface UserVote{
  userVoteID: number;
  user: User;
  nominationID: Nomination;
}
