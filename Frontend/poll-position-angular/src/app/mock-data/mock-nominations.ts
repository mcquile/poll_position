import {Nomination} from "../models/nomination";
import {POLLS} from "./mock-polls";

export const NOMINATIONS: Nomination[] = [
  {
    nominationID: 1,
    poll: POLLS[0],
    nominee: "Example Nominee 1",
    nominator: POLLS[0].pollCreator
  },
  {
    nominationID: 2,
    poll: POLLS[0],
    nominee: "Example Nominee 2",
    nominator: POLLS[0].pollCreator
  },
  {
    nominationID: 3,
    poll: POLLS[0],
    nominee: "Example Nominee 3",
    nominator: POLLS[0].pollCreator
  },
  {
    nominationID: 4,
    poll: POLLS[0],
    nominee: "Example Nominee 4",
    nominator: POLLS[0].pollCreator
  },
  {
    nominationID: 5,
    poll: POLLS[1],
    nominee: "Example Nominee 1",
    nominator: POLLS[1].pollCreator
  },
  {
    nominationID: 6,
    poll: POLLS[1],
    nominee: "Example Nominee 2",
    nominator: POLLS[1].pollCreator
  },
  {
    nominationID: 7,
    poll: POLLS[0],
    nominee: "Example Nominee 3",
    nominator: POLLS[0].pollCreator
  },
  {
    nominationID: 8,
    poll: POLLS[1],
    nominee: "Example Nominee 4",
    nominator: POLLS[1].pollCreator
  },
  {
    nominationID: 9,
    poll: POLLS[2],
    nominee: "Example Nominee 1",
    nominator: POLLS[2].pollCreator
  },
  {
    nominationID: 10,
    poll: POLLS[2],
    nominee: "Example Nominee 2",
    nominator: POLLS[0].pollCreator
  },
  {
    nominationID: 11,
    poll: POLLS[2],
    nominee: "Example Nominee 3",
    nominator: POLLS[0].pollCreator
  },
  {
    nominationID: 12,
    poll: POLLS[3],
    nominee: "Example Nominee 1",
    nominator: POLLS[0].pollCreator
  },
  {
    nominationID: 13,
    poll: POLLS[3],
    nominee: "Example Nominee 2",
    nominator: POLLS[0].pollCreator
  }
]
