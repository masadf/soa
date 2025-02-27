import {Avatar, ListItemAvatar, ListItemButton} from "@mui/material";
import Grid from "@mui/material/Grid2";


export const MarineRow = ({marine, onClick}) => {
    return (
        <ListItemButton onClick={onClick}>
            <Grid direction="row" justifyContent={"space-between"} container sx={{
                width: "100%",
            }}>
                <Grid container direction="row" spacing={4}>
                    <ListItemAvatar>
                        <Avatar
                            variant={"rounded"}
                            sx={{width: 128, height: 128}}
                            src={`${process.env.PUBLIC_URL}/static/images/avatar/${marine.category}.jpg`}
                        />
                    </ListItemAvatar>
                    <Grid item sx={{fontWeight: "bold", fontSize: 20}} alignContent="center">
                        {marine.name}
                    </Grid>
                </Grid>
                {marine.health && (
                    <Grid item sx={{color: "red", fontWeight: "bold", fontSize: 20}} alignContent="center">
                        {marine.health} ХП
                    </Grid>
                )}
            </Grid>
        </ListItemButton>
    )
}