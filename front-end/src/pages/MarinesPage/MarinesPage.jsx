import {List, Pagination, Stack, Typography} from "@mui/material";
import {useMarinesQuery} from "../../hooks/useMarinesQuery";
import Grid from "@mui/material/Grid2";
import {MarineRow} from "../../components/MarineRow/MarineRow";
import {MarineDetails} from "../../components/MarineDetails/MarineDetails";
import React, {useState} from "react";
import {Alert, SpeedDial, SpeedDialAction, SpeedDialIcon} from "@mui/lab";
import {MarineCreationModal} from "../../components/MarineCreationModal/MarineCreationModal";
import {useSearchParams} from "react-router-dom";
import {Loader} from "../../components/Loader/Loader";
import {useMarinesCalculateAvgQuery} from "../../hooks/useMarinesCalculateAvgQuery";
import AddIcon from '@mui/icons-material/Add';
import FilterListIcon from '@mui/icons-material/FilterList';
import SwapVertIcon from '@mui/icons-material/SwapVert';
import {FilterModal} from "../../components/FilterModal/FilterModal";
import {SortModal} from "../../components/SortModal/SortModal";
import {GroupingModal} from "../../components/GroupingModal/GroupingModal";
import GroupIcon from '@mui/icons-material/Group';

export const MarinesPage = () => {
    const [searchParams] = useSearchParams();
    const {avgStatus, reloadAvg} = useMarinesCalculateAvgQuery()
    const {
        marines,
        loadMarines,
        reloadMarines
    } = useMarinesQuery(searchParams.getAll("filterBy"), searchParams.getAll("sortBy"))
    const [openedMarine, openDetails] = useState(null)
    const [openedFilter, openFilter] = useState(null)
    const [openedSorter, openSorter] = useState(null)
    const [openedGrouping, openGrouping] = useState(null)
    const [isMarineCreation, createMarine] = useState(false)

    const AvgHealth = () => {
        if (avgStatus.isLoading) return <Loader/>
        if (avgStatus.isFailed) {
            return <Alert severity="error" sx={{margin: 1}}>
                Сервис временно недоступен
            </Alert>
        }
        return <Alert severity="info" sx={{margin: 1}}>
            Среднее здоровье равно: {avgStatus.data.result}
        </Alert>
    }

    if (marines.isLoading) {
        return <Loader/>
    }

    if (marines.isFailed) {
        return <Alert severity="error" sx={{margin: 1}}>
            Сервис временно недоступен
        </Alert>
    }


    return (
        <Stack>
            {marines.data.items.length !== 0 &&
                (<Grid justifyItems={"center"}>

                    <AvgHealth/>
                    <Pagination size="large" variant={"text"} sx={{paddingY: 5}} count={marines.data.totalPage}
                                page={marines.data.page}
                                onChange={(e, value) => {
                                    loadMarines(value)
                                }}/>
                </Grid>)
            }
            <List>
                {marines.data.items.map(el =>
                    <MarineRow marine={el} onClick={() => openDetails(el)}/>
                )}
                {marines.data.items.length === 0 &&
                    <Typography variant="h6" color={"grey"}>
                        Ничего не найдено
                    </Typography>
                }
            </List>
            {!!openedMarine &&
                <MarineDetails allowDelete allowUpdate shipId={searchParams.get("shipId")} refetch={() => {
                    reloadMarines()
                    reloadAvg()
                }} marine={openedMarine}
                               onClose={() => openDetails(null)}/>}
            <SpeedDial

                sx={{position: 'fixed', bottom: 24, right: 24}}
                icon={<SpeedDialIcon/>}
                ariaLabel={"marine creation"}
            >
                <SpeedDialAction
                    icon={<AddIcon/>}
                    onClick={() => createMarine(true)}
                />
                <SpeedDialAction
                    icon={<FilterListIcon/>}
                    onClick={() => openFilter(true)}
                />
                <SpeedDialAction
                    icon={<SwapVertIcon/>}
                    onClick={() => openSorter(true)}
                />
                <SpeedDialAction
                    icon={<GroupIcon/>}
                    onClick={() => openGrouping(true)}
                />
            </SpeedDial>
            <MarineCreationModal refetch={() => {
                reloadMarines()
                reloadAvg()
            }} open={isMarineCreation}
                                 onClose={() => createMarine(false)}/>
            <FilterModal open={openedFilter}
                         reloadMarines={reloadMarines}
                         onClose={() => openFilter(false)}/>
            <SortModal open={openedSorter}
                       reloadMarines={reloadMarines}
                       onClose={() => openSorter(false)}/>
            <GroupingModal
                open={openedGrouping}
                onClose={() => openGrouping(false)}
            />
        </Stack>
    )
}

