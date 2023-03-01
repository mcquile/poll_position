import {User} from "./user";
import {Nomination} from "./nomination";

export interface UserVote{
  userVote: number;
  userID: User;
  nominationID: Nomination;
}
