import {Box, Button, Modal, Stack, Typography} from "@mui/material";
import Grid from "@mui/material/Grid2";
import React from "react";
import {useGroupingByNameQuery} from "../../hooks/useGroupingByNameQuery";
import {Loader} from "../Loader/Loader";

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    bgcolor: 'background.paper',
    width: "40vw",
    boxShadow: 24,
    p: 4,
};

export const GroupingModal = ({open, onClose}) => {
    const {groupByNameStatus} = useGroupingByNameQuery()

    if (groupByNameStatus.isLoading) return <Loader/>

    return (<Modal open={open} onClose={onClose}>
        <Box sx={style}>
            <Grid container spacing={3}>
                <Grid container width={"100%"} direction={"row"} spacing={1}>
                    <Stack item spacing={1}>
                        {groupByNameStatus.data.map(item =>
                            <Grid><Typography>Имя: {item.field}</Typography> <Typography>Количество:
                                {item.size}</Typography></Grid>
                        )}
                    </Stack>
                </Grid>
                <Grid container justifyContent="space-between" width={"100%"}>
                    <Button variant={"contained"} onClick={onClose} color="inherit">Назад</Button>
                </Grid>
            </Grid>
        </Box>
    </Modal>)
}