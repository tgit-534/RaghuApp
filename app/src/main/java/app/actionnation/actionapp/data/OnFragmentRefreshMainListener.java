package app.actionnation.actionapp.data;

import app.actionnation.actionapp.Database_Content.TeamGame;

public interface OnFragmentRefreshMainListener {
    void refreshMain(TeamGame tamGame);
    void refreshMain(String documentGameId);
}
