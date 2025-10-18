import express, { Express } from "express";
import { dummy, getNames, load, save } from './routes';

// Configure and start the HTTP server.
const port: number = 8088;
const app: Express = express();
app.get("/api/dummy", dummy);
app.listen(port, () => console.log(`Server listening on ${port}`));

app.get("/api/getNames", getNames)
app.post("/api/save", save)
app.get("/api/load", load)