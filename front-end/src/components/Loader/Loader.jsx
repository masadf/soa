import {CircularProgress} from "@mui/material";
import React from "react";

const styleProgress = {
    position: 'absolute',
    top: '50%',
    left: '50%',
};

export const Loader = () => {
    return <CircularProgress sx={styleProgress}/>
}