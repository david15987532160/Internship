using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class GamePlayController : MonoBehaviour {

    public static GamePlayController instance;

    [SerializeField]
    private Button instructionButton;

    [SerializeField]
    private Text scoreText, endScoreText, bestScoreText;

    [SerializeField]
    private GameObject gameOverPanel;

    void Awake()
    {
        Time.timeScale = 0;
        _MakeInstance();
    }

    void _MakeInstance()
    {
        if(instance == null)
        {
            instance = this;
        }
    }

    public void _InstrucButton()
    {
        Time.timeScale = 1;
        instructionButton.gameObject.SetActive(false);
    }

    public void _SetScore(int score)
    {
        scoreText.text = "" + score;
    }

    public void _BirdDiedShowPanel(int score)
    {
        gameOverPanel.SetActive(true);

        endScoreText.text = "" + score;
        if(score > GameManager.instance.GetHighScore())
        {
            GameManager.instance.SetHighScore(score);
        }
        bestScoreText.text = "" + GameManager.instance.GetHighScore();
    }

    public void _MenuButton()
    {
        SceneManager.LoadScene("MainMenu");
    }

    public void _RestartGameButton()
    {
        SceneManager.LoadScene("GamePlay");
        //Application.LoadLevel(Application.loadedLevel);
    }
}
