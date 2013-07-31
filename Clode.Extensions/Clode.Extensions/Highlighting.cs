using ICSharpCode.AvalonEdit.Highlighting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Clode.Extensions
{
    public class Highlighting
    {
        public static void Register(String language, String[] fileExtensions, IHighlightingDefinition highlightingDefinition)
        {
            HighlightingManager.Instance.RegisterHighlighting(language, fileExtensions, highlightingDefinition);
        }
    }
}
