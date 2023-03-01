import {UserVote} from "../models/userVote";
import {USERS} from "./mock-users";
import {NOMINATIONS} from "./mock-nominations";

export const USERVOTES: UserVote[] = [
  {
    userVoteID: 1,
    user: USERS[1],
    nomination: NOMINATIONS[4]
  },
  {
    userVoteID: 1,
    user: USERS[2],
    nomination: NOMINATIONS[5]
  },
  {
    userVoteID: 1,
    user: USERS[3],
    nomination: NOMINATIONS[5]
  },
  {
    userVoteID: 1,
    user: USERS[0],
    nomination: NOMINATIONS[5]
  },
]
