import {Poll} from "../models/poll";
import {USERS} from "./mock-users";

export const POLLS: Poll[] = [
  {
    pollID: "1",
    pollCreator: USERS[0],
    title: "User Created Poll 1",
    description: "Description of user created poll 1",
    voteStart: new Date(2023, 2, 2, 8,0,0),
    voteEnd: new Date(2023, 2, 5, 8,0,0),
nominationEndTime: new Date(2023, 2, 1, 8,0,0),
pollCreationTime: new Date(2023, 1, 28, 8,0,0),
},
  {
    pollID: "2",
    pollCreator: USERS[1],
    title: "User Created Poll 2",
    description: "Description of user created poll 2",
    voteStart: new Date(2023, 2, 2, 8,0,0),
    voteEnd: new Date(2023, 2, 1, 8,0,0),
    nominationEndTime: new Date(2023, 2, 1, 8,0,0),
    pollCreationTime: new Date(2023, 1, 28, 8,0,0),
  },
  {
    pollID: "3",
    pollCreator: USERS[2],
    title: "User Created Poll 3",
    description: "Description of user created poll 3",
    voteStart: new Date(2023, 2, 2, 8,0,0),
    voteEnd: new Date(2023, 2, 5, 8,0,0),
    nominationEndTime: new Date(2023, 2, 1, 8,0,0),
    pollCreationTime: new Date(2023, 1, 28, 8,0,0),
  },
  {
    pollID: "4",
    pollCreator: USERS[3],
    title: "User Created Poll 4",
    description: "Description of user created poll 4",
    voteStart: new Date(2023, 2, 2, 8,0,0),
    voteEnd: new Date(2023, 2, 5, 8,0,0),
    nominationEndTime: new Date(2023, 2, 1, 8,0,0),
    pollCreationTime: new Date(2023, 1, 28, 8,0,0),
  }
];
