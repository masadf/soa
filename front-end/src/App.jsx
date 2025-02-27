import './App.css';
import {AppBar, Stack, Typography} from "@mui/material";
import Grid from '@mui/material/Grid2';
import {MarinesPage} from "./pages/MarinesPage/MarinesPage";
import React from "react";
import {ShipPage} from "./pages/ShipsPage/ShipPage";
import {BrowserRouter, HashRouter, Link, Route, Routes} from "react-router-dom";
import {CrewPage} from "./pages/CrewPage/CrewPage";
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';


function App() {
    return (
        <HashRouter>
            <Stack>
                <ToastContainer/>
                <AppBar position="static">
                    <Grid container justifyContent="center" spacing={3} columns={2} alignContent="center">
                        <Grid item sx={{padding: 3}}>
                            <Link to="/ships" style={{textDecoration: 'none'}}>
                                <Typography color={"white"} fontWeight="bold">
                                    Корабли
                                </Typography>
                            </Link>
                        </Grid>
                        <Grid item sx={{padding: 3}}>
                            <Link to="/marines" style={{textDecoration: 'none'}}>
                                <Typography color={"white"} fontWeight="bold">
                                    Десантники
                                </Typography>
                            </Link>
                        </Grid>
                    </Grid>
                </AppBar>
                <Grid container size={3}>
                    <Grid item size="grow"/>
                    <Grid item size={8}>
                        <Routes>
                            <Route path="/">
                                <Route path="ships" element={<ShipPage/>}/>
                                <Route path="ships/:shipId/marines" element={<CrewPage/>}/>
                                <Route path="marines" element={<MarinesPage/>}/>
                            </Route>
                        </Routes>
                    </Grid>
                    <Grid item size="grow"/>
                </Grid>
            </Stack>
        </HashRouter>
    );
}

export default App;
