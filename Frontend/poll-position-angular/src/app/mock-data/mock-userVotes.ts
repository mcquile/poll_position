import {UserVote} from "../models/userVote";
import {USERS} from "./mock-users";
import {NOMINATIONS} from "./mock-nominations";

export const USERVOTES: UserVote[] = [
  {
    userVoteID: 1,
    user: USERS[1],
    nomination: NOMINATIONS[0]
  },
]
