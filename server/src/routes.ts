import { Request, Response } from "express";
import { ParamsDictionary } from "express-serve-static-core";
//import { compact_list } from "./list";

type household = {name: string, joinCode: string, parents: string[], children: string[]}

//create a list to store households
//const households: household[] = [];

// Require type checking of request body.
type SafeRequest = Request<ParamsDictionary, {}, Record<string, unknown>>;
type SafeResponse = Response;  // only writing, so no need to check

/**
 * Returns a greeting message if "name" is provided in query params
 * @param req request to respond to
 * @param res object to send response with
 */
export const dummy = (req: SafeRequest, res: SafeResponse): void => {
  const name = first(req.query.name);
  if (name === undefined) {
    res.status(400).send('missing "name" parameter');
    return;
  }

  res.send({greeting: `Hi, ${name}`});
};


/** Returns a list of all the named save files. */
export const login = (req: SafeRequest, res: SafeResponse): void => {
  console.log("GOT login REQUEST ON SERVER");

  // Extract the joinCode from the query parameters
  const joinCode = first(req.query.joinCode);
  console.log(joinCode);
  // Validate the joinCode
  if (!joinCode) {
    res.json({ status: false });
    return;
  }

  // Example: Mock household data
  const households: household[] = [
    { name: "Smith Family", joinCode: "ABCD1234", parents: ["Alice", "Bob"], children: ["Charlie", "Daisy"] },
    { name: "Johnson Family", joinCode: "EFGH5678", parents: ["Eve", "Frank"], children: ["Grace", "Hank"] },
  ];
  
  // Find the household with the matching joinCode
  const household = households.find(h => h.joinCode === joinCode);

  if (household) {
    // If the joinCode matches, return the household data
    res.json({ status: true, household });
  } else {
    // If the joinCode does not match, return an error
    res.json({ status: false });
  }
};


/** Updates the last save for the give name to the post value. */
export const save = (_req: SafeRequest, _res: SafeResponse): void => {
  // TODO: Implement save route function

  // if (typeof _req.body.name === "string") {
  //   if (_req.body.content === undefined) {
  //     _res.status(400).send('missing "content" in POST body')
  //   } else {
  //     saved = saved.set_value(_req.body.name, _req.body.content);
  //     _res.send({saved: true})
  //   }
  // } else {
  //   _res.status(400).send('missing or invalid "name" in POST body')
  // }
};


/** Returns the last save value or null if none. */
export const load = (_req: SafeRequest, _res: SafeResponse): void => {
  // TODO: Implement load route function

  // if (typeof _req.query.name === "string") {
  //   if (saved.contains_key(_req.query.name)) {
  //     _res.send({name: _req.query.name, content: saved.get_value(_req.query.name)})
  //   } else {
  //     _res.send({name: _req.query.name, content: null})
  //   }

  // } else {
  //   _res.status(400).send('required argument "name" was missing')
  // }
};


// Helper to return the (first) value of the parameter if any was given.
// (This is mildly annoying because the client can also give mutiple values,
// in which case, express puts them into an array.)
const first = (param: unknown): string|undefined => {
  if (Array.isArray(param)) {
    return first(param[0]);
  } else if (typeof param === 'string') {
    return param;
  } else {
    return undefined;
  }
};
